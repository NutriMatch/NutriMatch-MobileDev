package com.akmalmf.nutrimatch.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:22
 * akmalmf007@gmail.com
 */
data class StatisticModel(
    @SerializedName("current") var current: Double,
    @SerializedName("target") var target: Double
)
