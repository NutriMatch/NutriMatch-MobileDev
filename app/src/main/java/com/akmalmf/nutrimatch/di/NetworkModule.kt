package com.akmalmf.nutrimatch.di

import com.akmalmf.nutrimatch.BuildConfig
import com.akmalmf.nutrimatch.core.data.source.remote.interceptor.AuthInterceptor
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.AuthApiService
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.MasterApiService
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.ProfileApiService
import com.akmalmf.nutrimatch.core.domain.repository.SharePrefRepository
import com.akmalmf.nutrimatch.di.qualifiers.AuthenticatedRetrofitClient
import com.akmalmf.nutrimatch.di.qualifiers.NotAuthenticatedRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 22:47
 * akmalmf007@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return logging
    }

    @Singleton
    @Provides
    fun providesJWTInterceptor(sharePrefRepository: SharePrefRepository): Interceptor =
        AuthInterceptor(sharePrefRepository.getAccessToken())

    @Singleton
    @Provides
    @AuthenticatedRetrofitClient
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        JWTInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(JWTInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @AuthenticatedRetrofitClient
    fun provideRetrofitClient(@AuthenticatedRetrofitClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @NotAuthenticatedRetrofitClient
    fun providesOkHttpClientNotAuthenticate(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @NotAuthenticatedRetrofitClient
    fun provideRetrofitClientNotAuth(@NotAuthenticatedRetrofitClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideAuthApiService(@NotAuthenticatedRetrofitClient retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideProfileApiService(@AuthenticatedRetrofitClient retrofit: Retrofit): ProfileApiService =
        retrofit.create(ProfileApiService::class.java)

    @Singleton
    @Provides
    fun providesMasterApiService(@AuthenticatedRetrofitClient retrofit: Retrofit): MasterApiService =
        retrofit.create(MasterApiService::class.java)
}