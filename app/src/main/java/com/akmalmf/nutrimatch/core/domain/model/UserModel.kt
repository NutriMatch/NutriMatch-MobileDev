package com.akmalmf.nutrimatch.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:29
 * akmalmf007@gmail.com
 */
@Parcelize
data class UserModel(

    @field:SerializedName("birthday")
    val birthday: String,

    @field:SerializedName("body_measurement")
    val bodyMeasurement: BodyMeasurement,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String
) : Parcelable
