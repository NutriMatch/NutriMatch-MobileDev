package com.akmalmf.nutrimatch.core.domain.usecase.auth

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.LoginResponse
import com.akmalmf.nutrimatch.core.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 23:05
 * akmalmf007@gmail.com
 */
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<BaseApiResponse<LoginResponse>>> {
        return flow{
            emit(Resource.loading())
            emit(repository.login(email, password))
        }.flowOn(Dispatchers.IO)
    }
}