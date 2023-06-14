package com.akmalmf.nutrimatch.ui.splash

import androidx.lifecycle.ViewModel
import com.akmalmf.nutrimatch.core.domain.repository.SharePrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 02:17
 * akmalmf007@gmail.com
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharePref: SharePrefRepository
): ViewModel(){
    fun onLogin() = sharePref.isOnLogin()
}