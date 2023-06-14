package com.akmalmf.nutrimatch.core.domain.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/02 03:20
 * akmalmf007@gmail.com
 */
interface ProfileRepository {
    suspend fun profile(): Resource<BaseApiResponse<UserModel>>
    suspend fun editProfile(fullname: String, birthday: String): Resource<BaseApiResponse<String>>
    suspend fun editBodyMeasurement(height: Int, weight: Int, gender: String, activitiesLevel: String): Resource<BaseApiResponse<String>>
    suspend fun editPassword(oldPassword: String, newPassword: String): Resource<BaseApiResponse<String>>
}