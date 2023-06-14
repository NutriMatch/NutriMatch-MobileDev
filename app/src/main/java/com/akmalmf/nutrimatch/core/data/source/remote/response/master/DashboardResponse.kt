package com.akmalmf.nutrimatch.core.data.source.remote.response.master

import com.akmalmf.nutrimatch.core.domain.model.GraphModel
import com.akmalmf.nutrimatch.core.domain.model.HistoryFoodModel
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:23
 * akmalmf007@gmail.com
 */
data class DashboardResponse(
    @SerializedName("user") var user: UserModel,
    @SerializedName("graph") var graph: GraphModel,
    @SerializedName("history_food") var historyFood: HistoryFoodModel,
)
