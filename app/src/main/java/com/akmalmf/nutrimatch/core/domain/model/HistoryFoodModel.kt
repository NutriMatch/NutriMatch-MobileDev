package com.akmalmf.nutrimatch.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:26
 * akmalmf007@gmail.com
 */
data class HistoryFoodModel(
    @SerializedName("breakfast") var breakfast : List<DetailHistoryFoodModel>,
    @SerializedName("lunch") var lunch : List<DetailHistoryFoodModel>,
    @SerializedName("dinner") var dinner : List<DetailHistoryFoodModel>
)