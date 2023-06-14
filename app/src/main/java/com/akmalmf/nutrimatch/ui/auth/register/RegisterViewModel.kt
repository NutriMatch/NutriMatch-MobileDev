package com.akmalmf.nutrimatch.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.usecase.auth.CheckEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val checkEmailUseCase: CheckEmailUseCase
): ViewModel() {
    fun checkEmail(email: String): LiveData<Resource<BaseApiResponse<String>>> {
        return checkEmailUseCase(email).asLiveData(Dispatchers.Main)
    }
}