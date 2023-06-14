package com.akmalmf.nutrimatch.core.domain.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.DashboardResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:20
 * akmalmf007@gmail.com
 */
interface MasterRepository {
    suspend fun dashboard(): Resource<BaseApiResponse<DashboardResponse>>

    suspend fun scanNutrition(
        imageFood: MultipartBody.Part,
        foodWeight: RequestBody
    ): Resource<BaseApiResponse<List<ScanNutritionResponse>>>

    suspend fun submitFood(
        imageFood: MultipartBody.Part,
        food: Map<String, RequestBody>
    ): Resource<BaseApiResponse<String>>

    suspend fun submitManual(
        imageFood: MultipartBody.Part,
        name: RequestBody,
        weight: RequestBody,
        calories: RequestBody
    ): Resource<BaseApiResponse<String>>
}