package com.akmalmf.nutrimatch.core.data.repository

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.RemoteDataSource
import com.akmalmf.nutrimatch.core.data.source.remote.network_service.ProfileApiService
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.core.domain.repository.ProfileRepository
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/02 04:09
 * akmalmf007@gmail.com
 */
class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApiService
) : ProfileRepository, RemoteDataSource() {
    override suspend fun profile(): Resource<BaseApiResponse<UserModel>> {
        return safeApiCall {
            api.profile()
        }
    }

    override suspend fun editProfile(
        fullname: String,
        birthday: String
    ): Resource<BaseApiResponse<String>> {
        return safeApiCall {
            api.editProfile(fullname, birthday)
        }
    }

    override suspend fun editBodyMeasurement(
        height: Int,
        weight: Int,
        gender: String,
        activitiesLevel: String
    ): Resource<BaseApiResponse<String>> {
        return safeApiCall {
            api.editBodyMeasurements(height, weight, gender, activitiesLevel)
        }
    }

    override suspend fun editPassword(
        oldPassword: String,
        newPassword: String
    ): Resource<BaseApiResponse<String>> {
        return safeApiCall {
            api.editPassword(oldPassword, newPassword)
        }
    }

}