package com.akmalmf.nutrimatch.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:25
 * akmalmf007@gmail.com
 */
data class GraphModel(
    @SerializedName("calories") var calories: StatisticModel,
    @SerializedName("carbs") var carbs: StatisticModel,
    @SerializedName("fat") var fat: StatisticModel,
    @SerializedName("protein") var protein: StatisticModel,
)