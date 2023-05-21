package com.akmalmf.nutrimatch.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:29
 * akmalmf007@gmail.com
 */
data class BodyMeasurement(

    @field:SerializedName("activity_level")
    val activityLevel: Int,

    @field:SerializedName("gender")
    val gender: Int,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("height")
    val height: Int
)