package com.akmalmf.nutrimatch.core.domain.usecase.profile

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/02 04:16
 * akmalmf007@gmail.com
 */
class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(): Flow<Resource<BaseApiResponse<UserModel>>> = flow{
        emit(Resource.loading())
        emit(repository.profile())
    }
}