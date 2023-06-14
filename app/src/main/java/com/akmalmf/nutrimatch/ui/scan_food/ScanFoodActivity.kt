package com.akmalmf.nutrimatch.ui.scan_food

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.ui.main.profile.edit.EditProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_food)

        val navController = findNavController(R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.scan_food_navigation)
        navController.setGraph(navGraph, intent.extras)
    }

    companion object {
        const val FLAG_URI = "flagUri"

        fun start(context: Context, uri: Uri) {
            context.startActivity(
                Intent(context, ScanFoodActivity::class.java).apply {
                    putExtra(FLAG_URI, uri.toString())
                }
            )
        }
    }
}