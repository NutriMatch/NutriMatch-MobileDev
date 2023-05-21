package com.akmalmf.nutrimatch.abstraction.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.data.HttpResult
import com.akmalmf.nutrimatch.utils.NetworkConnectivityLiveData
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 03:03
 * akmalmf007@gmail.com
 */
abstract class BaseActivity : AppCompatActivity() {
    private var isOnline = false

    abstract fun initView()

    abstract fun initObservable()

    override fun onStart() {
        super.onStart()
        NetworkConnectivityLiveData(application).observe(this) {
            isOnline = it
            if (!isOnline) {
                showNoConnectionAlert()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkConnection()
        initView()
        initObservable()
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun snackBarSuccess(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.green_500))
            .show()
    }

    fun snackBarError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_400))
            .show()
    }

    fun snackBarErrorLong(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_400))
            .show()
    }

    fun snackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }


    fun showNoConnectionAlert(
        errorMessage: String = getString(R.string.no_connection_error_message),
    ) {
        snackBarError(errorMessage)
    }

    private fun showTimeoutAlert(
        errorMessage: String = "Koneksi timeout. Mohon coba beberapa saat lagi",
    ) {
        snackBarError(errorMessage)
    }

    fun showClientAlert(
        errorMessage: String = "Terjadi kesalahan pada aplikasi. Mohon coba beberapa saat lagi",
        message: String? = null
    ) {
        if (message != null) {
            snackBarError(message)
        } else {
            snackBarError(errorMessage)
        }
    }

    fun showServerErrorAlert(
        errorMessage: String = "Terjadi kendala pada server. Mohon coba beberapa saat lagi",
    ) {
        snackBarError(errorMessage)
    }

    fun showUnknownErrorAlert(
        errorMessage: String = "Terjadi kesalahan yang tak terduga. " +
                "Mohon hubungi customer service kami untuk bantuan lebih lanjut",
    ) {
        snackBarError(errorMessage)
    }

    private fun checkConnection() {
        val cm: ConnectivityManager = applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        isOnline = cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun showErrorAlert(cause: HttpResult, message: String? = null) {
        when (cause) {
            HttpResult.NO_CONNECTION -> showNoConnectionAlert()
            HttpResult.TIMEOUT -> showTimeoutAlert()
            HttpResult.CLIENT_ERROR -> showClientAlert(message = message)
            HttpResult.BAD_RESPONSE -> showUnknownErrorAlert()
            HttpResult.SERVER_ERROR -> showServerErrorAlert()
            HttpResult.NOT_DEFINED -> showUnknownErrorAlert()
        }
    }
}