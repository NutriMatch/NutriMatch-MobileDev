package com.akmalmf.nutrimatch.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.nutrimatch.core.data.source.local.SharedPrefImpl
import com.akmalmf.nutrimatch.core.data.source.remote.response.auth.LoginResponse
import com.akmalmf.nutrimatch.core.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharePref: SharedPrefImpl
) : ViewModel() {
    fun login(email: String, password: String) = loginUseCase.invoke(email, password).asLiveData(Dispatchers.Main)
    fun saveUser(data: LoginResponse) {
        sharePref.setAccessToken(data.token)
    }
}