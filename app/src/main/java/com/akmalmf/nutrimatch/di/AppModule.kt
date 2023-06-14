package com.akmalmf.nutrimatch.di

import android.content.Context
import com.akmalmf.nutrimatch.core.data.repository.AuthRepositoryImpl
import com.akmalmf.nutrimatch.core.data.repository.MasterRepositoryImpl
import com.akmalmf.nutrimatch.core.data.repository.ProfileRepositoryImpl
import com.akmalmf.nutrimatch.core.data.source.local.SharedPrefImpl
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.AuthApiService
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.MasterApiService
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.ProfileApiService
import com.akmalmf.nutrimatch.core.domain.repository.AuthRepository
import com.akmalmf.nutrimatch.core.domain.repository.MasterRepository
import com.akmalmf.nutrimatch.core.domain.repository.ProfileRepository
import com.akmalmf.nutrimatch.core.domain.repository.SharePrefRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 22:47
 * akmalmf007@gmail.com
 */
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesMasterRepository(api: MasterApiService): MasterRepository {
        return MasterRepositoryImpl(api)
    }


    @Singleton
    @Provides
    fun providesProfileRepository(api: ProfileApiService): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }
}