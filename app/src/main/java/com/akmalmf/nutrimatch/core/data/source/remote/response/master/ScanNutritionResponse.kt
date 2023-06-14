package com.akmalmf.nutrimatch.core.data.source.remote.response.master

import android.os.Parcelable
import com.akmalmf.nutrimatch.core.domain.model.NutritionInfoModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:35
 * akmalmf007@gmail.com
 */
@Parcelize
data class ScanNutritionResponse(
    @SerializedName("food_title") var foodTitle: String,
    @SerializedName("nutrition_info") var nutritionInfo: NutritionInfoModel,
) : Parcelable