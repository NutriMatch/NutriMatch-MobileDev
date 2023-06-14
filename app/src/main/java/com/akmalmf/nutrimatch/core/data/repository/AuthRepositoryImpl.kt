package com.akmalmf.nutrimatch.core.data.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.RemoteDataSource
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.AuthApiService
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.LoginResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.RegisterResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.core.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 22:50
 * akmalmf007@gmail.com
 */

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService
) : AuthRepository, RemoteDataSource() {
    override suspend fun login(
        email: String, password: String
    ): Resource<BaseApiResponse<LoginResponse>> {
        return safeApiCall {
            api.login(email, password)
        }
    }

    override suspend fun checkEmail(email: String): Resource<BaseApiResponse<String>> {
        return safeApiCall {
            api.checkEmail(email)
        }
    }

    override suspend fun register(
        user: UserModel,
        password: String
    ): Resource<BaseApiResponse<RegisterResponse>> {
        return safeApiCall {
            api.register(
                user.fullname,
                user.birthday,
                user.email,
                password,
                user.bodyMeasurement.height,
                user.bodyMeasurement.weight,
                user.bodyMeasurement.gender,
                user.bodyMeasurement.activityLevel
            )
        }
    }
}