package com.akmalmf.nutrimatch.core.data.source.remote.network_service

import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.LoginResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:59
 * akmalmf007@gmail.com
 */
interface AuthApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseApiResponse<LoginResponse>

    @FormUrlEncoded
    @POST("auth/check_email")
    suspend fun checkEmail(
        @Field("email") email: String
    ): BaseApiResponse<String>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("fullname") name: String,
        @Field("birthday") birthday: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("gender") gender: String,
        @Field("activity_level") activityLevel: String,
    ): BaseApiResponse<RegisterResponse>

}