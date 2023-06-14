package com.akmalmf.nutrimatch.core.data.source.remote.network_service

import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 03:00
 * akmalmf007@gmail.com
 */
interface ProfileApiService{
    @GET("profile")
    suspend fun profile(): BaseApiResponse<UserModel>
    @FormUrlEncoded
    @PUT("profile/account")
    suspend fun editProfile(
        @Field("fullname") fullname: String,
        @Field("birthday") birthday: String,
    ): BaseApiResponse<String>
    @FormUrlEncoded
    @PUT("profile/account_settings")
    suspend fun editBodyMeasurements(
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("gender") gender: String,
        @Field("activity_level") activities_level: String,
    ): BaseApiResponse<String>

    @FormUrlEncoded
    @PUT("profile/password")
    suspend fun editPassword(
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String,
    ): BaseApiResponse<String>

}