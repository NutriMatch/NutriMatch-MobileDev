package com.akmalmf.nutrimatch.core.data.source.remote.response.auth

import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("body_measurement_setting")
	val bodyMeasurementSetting: Boolean,

    @field:SerializedName("user")
	val user: UserModel,

    @field:SerializedName("token")
	val token: String
)
