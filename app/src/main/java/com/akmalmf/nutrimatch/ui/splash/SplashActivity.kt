package com.akmalmf.nutrimatch.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.ui.auth.AuthActivity
import com.akmalmf.nutrimatch.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if(viewModel.onLogin()){
                MainActivity.start(this)
            } else {
                AuthActivity.start(this)
            }
            finishAffinity()
        }, 2100)
    }
}