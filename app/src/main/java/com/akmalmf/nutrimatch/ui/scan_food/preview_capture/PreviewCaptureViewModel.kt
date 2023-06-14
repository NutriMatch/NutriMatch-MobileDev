package com.akmalmf.nutrimatch.ui.scan_food.preview_capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.core.domain.usecase.master.ScanNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 16:18
 * akmalmf007@gmail.com
 */
@HiltViewModel
class PreviewCaptureViewModel @Inject constructor(
    private val scanNutritionUseCase: ScanNutritionUseCase
) : ViewModel() {
    fun scanNutrition(
        foodImage: File,
        foodWeight: Int
    ): LiveData<Resource<BaseApiResponse<List<ScanNutritionResponse>>>> {
        val requestImageFile =
            foodImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = requestImageFile.let { it1 ->
            MultipartBody.Part.createFormData(
                "food_image",
                foodImage.name,
                it1
            )
        }

        return scanNutritionUseCase(
            imageMultipart,
            foodWeight.toString().toRequestBody("text/plain".toMediaType())
        ).asLiveData(Dispatchers.Main)
    }
}