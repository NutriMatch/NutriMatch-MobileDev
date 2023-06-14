package com.akmalmf.nutrimatch.ui.splash

import androidx.lifecycle.ViewModel
import com.akmalmf.nutrimatch.core.data.source.local.SharedPrefImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:17
 * akmalmf007@gmail.com
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharePref: SharedPrefImpl
): ViewModel(){
    fun onLogin() = sharePref.isOnLogin()
}