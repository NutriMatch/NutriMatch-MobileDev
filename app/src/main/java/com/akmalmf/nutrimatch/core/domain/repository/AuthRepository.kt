package com.akmalmf.nutrimatch.core.domain.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.LoginResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.RegisterResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:32
 * akmalmf007@gmail.com
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<BaseApiResponse<LoginResponse>>
    suspend fun checkEmail(email: String): Resource<BaseApiResponse<String>>

    suspend fun register(user: UserModel, password: String): Resource<BaseApiResponse<RegisterResponse>>
}