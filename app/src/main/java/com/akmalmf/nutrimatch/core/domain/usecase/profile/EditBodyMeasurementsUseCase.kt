package com.akmalmf.nutrimatch.core.domain.usecase.profile

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 05:28
 * akmalmf007@gmail.com
 */
class EditBodyMeasurementsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(
        height: Int,
        weight: Int,
        gender: String,
        activitiesLevel: String
    ): Flow<Resource<BaseApiResponse<String>>> = flow {
        emit(Resource.loading())
        emit(repository.editBodyMeasurement(height, weight, gender, activitiesLevel))
    }
}