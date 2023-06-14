package com.akmalmf.nutrimatch.ui.main.profile.edit.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.usecase.profile.EditPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 04:04
 * akmalmf007@gmail.com
 */
@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    private val editPasswordUseCase: EditPasswordUseCase
): ViewModel() {
    fun editPassword(oldPassword: String, newPassword: String): LiveData<Resource<BaseApiResponse<String>>> {
        return editPasswordUseCase(oldPassword, newPassword).asLiveData(Dispatchers.Main)
    }
}