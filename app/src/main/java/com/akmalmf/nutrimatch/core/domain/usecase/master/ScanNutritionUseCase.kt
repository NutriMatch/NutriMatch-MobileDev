package com.akmalmf.nutrimatch.core.domain.usecase.master

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.core.domain.repository.MasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 16:27
 * akmalmf007@gmail.com
 */
class ScanNutritionUseCase @Inject constructor(
    private val repository: MasterRepository
) {
    operator fun invoke(
        imageFood: MultipartBody.Part,
        foodWeight: RequestBody
    ): Flow<Resource<BaseApiResponse<List<ScanNutritionResponse>>>> {
        return flow {
            emit(Resource.loading())
            emit(repository.scanNutrition(imageFood, foodWeight))
        }.flowOn(Dispatchers.IO)
    }
}