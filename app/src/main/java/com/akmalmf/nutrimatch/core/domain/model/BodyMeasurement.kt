package com.akmalmf.nutrimatch.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:29
 * akmalmf007@gmail.com
 */
@Parcelize
data class BodyMeasurement(

    @field:SerializedName("activity_level")
    val activityLevel: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("height")
    val height: Int
) : Parcelable