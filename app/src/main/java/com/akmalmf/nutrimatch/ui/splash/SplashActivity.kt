package com.akmalmf.nutrimatch.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.ui.auth.AuthActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            AuthActivity.start(this)
            finishAffinity()
        }, 2100)
    }
}