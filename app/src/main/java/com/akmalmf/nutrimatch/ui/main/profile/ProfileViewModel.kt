package com.akmalmf.nutrimatch.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.local.SharedPrefImpl
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.core.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val sharePref: SharedPrefImpl
): ViewModel() {
    private val _profile = MutableLiveData<Resource<BaseApiResponse<UserModel>>>()
    val profile: LiveData<Resource<BaseApiResponse<UserModel>>>
        get() = _profile
    fun getProfile() {
        getProfileUseCase().onEach {
            _profile.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun logout(){
        sharePref.clearPref()
    }
}