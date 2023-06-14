package com.akmalmf.nutrimatch.core.domain.usecase.master

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.repository.MasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SubmitManualUseCase @Inject constructor(
    private val repository: MasterRepository
) {
    operator fun invoke(
        imageFood: MultipartBody.Part,
        name: RequestBody,
        weight: RequestBody,
        calories: RequestBody
    ): Flow<Resource<BaseApiResponse<String>>> {
        return flow {
            emit(Resource.loading())
            emit(repository.submitManual(imageFood, name, weight, calories))
        }.flowOn(Dispatchers.IO)
    }
}