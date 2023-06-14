package com.akmalmf.nutrimatch.core.data.source.remote.network_service

import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.DashboardResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:59
 * akmalmf007@gmail.com
 */
interface MasterApiService {
    @GET("master/dashboard")
    suspend fun dashboard(): BaseApiResponse<DashboardResponse>

    @Multipart
    @POST("master/scan_nutrition")
    suspend fun scanNutrition(
        @Part imageFood: MultipartBody.Part,
        @Part("food_weight") foodWeight: RequestBody,
    ): BaseApiResponse<List<ScanNutritionResponse>>

    @Multipart
    @POST("master/submit_food")
    suspend fun submitFood(
        @Part foodImage: MultipartBody.Part,
        @PartMap food: Map<String, @JvmSuppressWildcards RequestBody>,
    ): BaseApiResponse<String>

    @Multipart
    @POST("master/submit_manual")
    suspend fun submitManual(
        @Part foodImage: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("calories") calories: RequestBody,
    ): BaseApiResponse<String>
}