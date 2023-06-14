package com.akmalmf.nutrimatch.ui.main.profile.edit.body_measurements

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.usecase.profile.EditBodyMeasurementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 05:27
 * akmalmf007@gmail.com
 */
@HiltViewModel
class EditBodyMeasurementsViewModel @Inject constructor(
    private val editBodyMeasurementsUseCase: EditBodyMeasurementsUseCase
) : ViewModel() {
    fun editBodyMeasurements(
        height: Int,
        weight: Int,
        gender: String,
        activitiesLevel: String
    ): LiveData<Resource<BaseApiResponse<String>>> {
        return editBodyMeasurementsUseCase(height, weight, gender, activitiesLevel).asLiveData(
            Dispatchers.Main
        )
    }
}