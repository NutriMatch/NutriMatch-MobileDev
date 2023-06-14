package com.akmalmf.nutrimatch.ui.main.profile.edit.account_information

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.usecase.profile.EditProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 11:20
 * akmalmf007@gmail.com
 */
@HiltViewModel
class EditAccountInformationViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase
) : ViewModel() {
    fun editAccountInformation(fullname: String, birthday: String): LiveData<Resource<BaseApiResponse<String>>> {
        return editProfileUseCase(fullname, birthday).asLiveData(Dispatchers.Main)
    }
}