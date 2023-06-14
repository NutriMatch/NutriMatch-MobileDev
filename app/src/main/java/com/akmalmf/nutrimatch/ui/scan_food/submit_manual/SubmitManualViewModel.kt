package com.akmalmf.nutrimatch.ui.scan_food.submit_manual

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.usecase.master.SubmitManualUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SubmitManualViewModel @Inject constructor(
    private val submitManualUseCase: SubmitManualUseCase
) : ViewModel() {
    fun submit(
        image: File,
        name: String,
        weight: Double,
        calories: Int
    ): LiveData<Resource<BaseApiResponse<String>>> {
        val requestImageFile =
            image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = requestImageFile.let { it1 ->
            MultipartBody.Part.createFormData(
                "food_image",
                image.name,
                it1
            )
        }
        val nameReqBody = name.toRequestBody("text/plain".toMediaType())
        val weightReqBody = weight.toString().toRequestBody("text/plain".toMediaType())
        val caloriesReqBody = calories.toString().toRequestBody("text/plain".toMediaType())

        return submitManualUseCase(
            imageMultipart,
            nameReqBody,
            weightReqBody,
            caloriesReqBody
        ).asLiveData(Dispatchers.Main)
    }
}