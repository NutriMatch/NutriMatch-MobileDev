package com.akmalmf.nutrimatch.ui.auth.body_measurement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.RegisterResponse
import com.akmalmf.nutrimatch.core.domain.model.BodyMeasurement
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.core.domain.usecase.auth.RegisterUseCase
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class BodyMeasurementViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    fun register(
        fullname: String,
        birthday: String,
        email: String,
        password: String,
        height: Int,
        weight: Int,
        gender: String,
        activityLevel: String
    ): LiveData<Resource<BaseApiResponse<RegisterResponse>>> {
        val bodyMeasurement = BodyMeasurement(activityLevel, gender, weight, height)
        val user = UserModel(birthday, bodyMeasurement, "reg", fullname, email)
        return registerUseCase(user, password).asLiveData(Dispatchers.Main)
    }
}