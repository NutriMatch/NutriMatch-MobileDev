package com.akmalmf.nutrimatch.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/10 11:35
 * akmalmf007@gmail.com
 */
data class FoodRequestModel(
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("protein") val protein: Double,
    @SerializedName("carb") val carb: Double,
    @SerializedName("fat") val fat: Double,
)