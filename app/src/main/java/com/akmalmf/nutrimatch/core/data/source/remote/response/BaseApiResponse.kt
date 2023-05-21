package com.akmalmf.nutrimatch.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:25
 * akmalmf007@gmail.com
 */
data class BaseApiResponse<D>(
    @SerializedName("success") val success: Boolean,

    @SerializedName("message") val message: String,

    @SerializedName("data") val dataResponse:D? = null
)