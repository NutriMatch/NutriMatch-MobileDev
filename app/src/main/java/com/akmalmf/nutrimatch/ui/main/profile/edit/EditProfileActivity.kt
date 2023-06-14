package com.akmalmf.nutrimatch.ui.main.profile.edit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.akmalmf.nutrimatch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val flag = intent.getStringExtra(EXTRA_FLAG)

        val navController = findNavController(R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.edit_profile_navigation)

        when (flag) {
            FLAG_EDIT_ACCOUNT_INFORMATION -> {
                navController.setGraph(navGraph, intent.extras)
            }
            FLAG_EDIT_PASSWORD -> {
                navGraph.setStartDestination(R.id.editPasswordFragment)
                navController.setGraph(navGraph, intent.extras)
            }
            FLAG_EDIT_BODY_MEASUREMENTS -> {
                navGraph.setStartDestination(R.id.editBodyMeasurementsFragment)
                navController.setGraph(navGraph, intent.extras)
            }
        }
    }

    companion object {
        const val FLAG_EDIT_ACCOUNT_INFORMATION = "flagEditAccountInformation"
        const val FLAG_EDIT_PASSWORD = "flagEditPassword"
        const val FLAG_EDIT_BODY_MEASUREMENTS = "flagEditBodyMeasurements"
        const val EXTRA_FLAG = "flag"

        fun start(context: Context, bundle: Bundle, flag: String) {
            context.startActivity(
                Intent(context, EditProfileActivity::class.java).apply {
                    putExtras(bundle)
                    putExtra(EXTRA_FLAG, flag)
                }
            )
        }

        fun start(context: Context, flag: String) {
            context.startActivity(
                Intent(context, EditProfileActivity::class.java).apply {
                    putExtra(EXTRA_FLAG, flag)
                }
            )
        }
    }
}