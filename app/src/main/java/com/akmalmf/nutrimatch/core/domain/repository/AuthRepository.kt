package com.akmalmf.nutrimatch.core.domain.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.LoginResponse

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:32
 * akmalmf007@gmail.com
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
}