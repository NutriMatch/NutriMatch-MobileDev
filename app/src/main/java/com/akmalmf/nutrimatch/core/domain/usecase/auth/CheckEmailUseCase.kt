package com.akmalmf.nutrimatch.core.domain.usecase.auth

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/10 16:32
 * akmalmf007@gmail.com
 */
class CheckEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String): Flow<Resource<BaseApiResponse<String>>> {
        return flow {
            emit(Resource.loading())
            emit(repository.checkEmail(email))
        }.flowOn(Dispatchers.IO)
    }
}