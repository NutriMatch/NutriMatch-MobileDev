package com.akmalmf.nutrimatch.core.data.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("token")
	val token: String? = null
)
