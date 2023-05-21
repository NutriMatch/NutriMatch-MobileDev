package com.akmalmf.nutrimatch.core.domain.model

import com.akmalmf.nutrimatch.core.domain.model.BodyMeasurement
import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:29
 * akmalmf007@gmail.com
 */
data class User(

    @field:SerializedName("birthday")
    val birthday: String,

    @field:SerializedName("body_measurement")
    val bodyMeasurement: BodyMeasurement,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
)
