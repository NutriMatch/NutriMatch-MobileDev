package com.akmalmf.nutrimatch.core.data.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.RemoteDataSource
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.MasterApiService
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.DashboardResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.core.domain.repository.MasterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:47
 * akmalmf007@gmail.com
 */
class MasterRepositoryImpl @Inject constructor(
    private val api: MasterApiService
) : MasterRepository, RemoteDataSource() {
    override suspend fun dashboard(): Resource<BaseApiResponse<DashboardResponse>> {
        return safeApiCall {
            api.dashboard()
        }
    }

    override suspend fun scanNutrition(
        imageFood: MultipartBody.Part,
        foodWeight: RequestBody
    ): Resource<BaseApiResponse<List<ScanNutritionResponse>>> {
        return safeApiCall {
            api.scanNutrition(imageFood, foodWeight)
        }
    }

    override suspend fun submitFood(
        imageFood: MultipartBody.Part,
        food: Map<String, RequestBody>
    ): Resource<BaseApiResponse<String>> {
        return safeApiCall {
            api.submitFood(imageFood, food);
        }
    }

    override suspend fun submitManual(
        imageFood: MultipartBody.Part,
        name: RequestBody,
        weight: RequestBody,
        calories: RequestBody
    ): Resource<BaseApiResponse<String>> {
        return safeApiCall {
            api.submitManual(imageFood, name, weight, calories)
        }
    }
}