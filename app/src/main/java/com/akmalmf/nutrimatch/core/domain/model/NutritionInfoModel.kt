package com.akmalmf.nutrimatch.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:28
 * akmalmf007@gmail.com
 */
@Parcelize
data class NutritionInfoModel(
    @SerializedName("weight") var weight: Double? = null,
    @SerializedName("calories") var calories: Double? = null,
    @SerializedName("protein") var protein: Double,
    @SerializedName("fat") var fat: Double,
    @SerializedName("carb") var carb: Double
) : Parcelable