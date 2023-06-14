package com.akmalmf.nutrimatch.ui.scan_food.nutritional_content

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.core.domain.model.FoodRequestModel
import com.akmalmf.nutrimatch.core.domain.usecase.master.SubmitFoodUseCase
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/10 11:42
 * akmalmf007@gmail.com
 */
@HiltViewModel
class NutritionalContentViewModel @Inject constructor(
    private val submitFoodUseCase: SubmitFoodUseCase
) : ViewModel() {
    fun submitFood(
        foodImage: File,
        foods: List<ScanNutritionResponse>
    ): LiveData<Resource<BaseApiResponse<String>>> {
        val foodRequest = foods.map { soruce ->
            FoodRequestModel(
                soruce.foodTitle,
                soruce.nutritionInfo.weight!!,
                soruce.nutritionInfo.protein,
                soruce.nutritionInfo.carb,
                soruce.nutritionInfo.fat
            )
        }

        val foodMap = foodRequest.mapIndexed { index, food ->
            listOf(
                "food[$index][name]" to food.name,
                "food[$index][weight]" to food.weight,
                "food[$index][protein]" to food.protein,
                "food[$index][carb]" to food.carb,
                "food[$index][fat]" to food.fat
            )
        }.flatten().toMap().mapValues { entry ->
            entry.value.toString().toRequestBody("text/plain".toMediaType())
        }
        val requestImageFile =
            foodImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = requestImageFile.let { it1 ->
            MultipartBody.Part.createFormData(
                "food_image",
                foodImage.name,
                it1
            )
        }

        return submitFoodUseCase(
            imageMultipart,
            foodMap
        ).asLiveData(Dispatchers.Main)
    }
}