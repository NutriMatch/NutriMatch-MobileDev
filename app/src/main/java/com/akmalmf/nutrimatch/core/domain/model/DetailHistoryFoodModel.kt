package com.akmalmf.nutrimatch.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:27
 * akmalmf007@gmail.com
 */
data class DetailHistoryFoodModel(
    @SerializedName("image_url") var imageUrl: String,
    @SerializedName("title") var title: String,
    @SerializedName("nutrition_info") var nutritionInfo: NutritionInfoModel,
)