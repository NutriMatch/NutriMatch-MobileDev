package com.akmalmf.nutrimatch.core.data.source.remote.interceptor

import com.akmalmf.nutrimatch.core.data.source.local.SharedPrefImpl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 23:33
 * akmalmf007@gmail.com
 */
@Singleton
class AuthInterceptor(private val sharedPrefImpl: SharedPrefImpl) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = if (request.header("No-Authentication") == null && sharedPrefImpl.getAccessToken().isNotEmpty()) {
            val finalToken = "Bearer ${sharedPrefImpl.getAccessToken()}"
            request.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        } else {
            request.newBuilder()
                .build()
        }
        return chain.proceed(request)
    }
}