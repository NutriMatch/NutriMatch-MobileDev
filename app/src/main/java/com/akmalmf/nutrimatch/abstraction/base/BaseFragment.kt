package com.akmalmf.nutrimatch.abstraction.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.data.HttpResult
import com.akmalmf.nutrimatch.utils.NetworkConnectivityLiveData
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/22 03:13
 * akmalmf007@gmail.com
 */
abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    private var _bi: VB? = null
    protected val bi: VB? get() = _bi

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    private var isOnline = false
    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bi = bindingInflater(inflater, container, false)
        return _bi!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bi = null
    }

    abstract fun initObservable()

    override fun onStart() {
        super.onStart()
        NetworkConnectivityLiveData(requireActivity().application).observe(viewLifecycleOwner) {
            isOnline = it
            if (!isOnline) {
                showNoConnectionAlert()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection()
        initView(savedInstanceState)
        initObservable()
    }

    fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun snackBarSuccess(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green_500))
            .show()
    }

    fun snackBarError(message: String) {
        Log.e(tag, message)
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_400))
            .show()
    }

    fun snackBarWarning(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange_700))
            .show()
    }

    fun snackBarErrorLong(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_400))
            .show()
    }

    fun snackBar(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    fun snackBarGreen500(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green_500))
            .show()
    }


    fun showNoConnectionAlert(
        errorMessage: String = getString(R.string.no_connection_error_message),
    ) {
        snackBarError(errorMessage)
    }

    private fun showTimeoutAlert(
        errorMessage: String = getString(R.string.timeout_error_message),
    ) {
        snackBarError(errorMessage)
    }

    fun showClientAlert(
        errorMessage: String = getString(R.string.client_error_message),
        message: String? = null
    ) {
        if (message != null) {
            snackBarError(message)
        } else {
            snackBarError(errorMessage)
        }
    }

    fun showServerErrorAlert(
        errorMessage: String = getString(R.string.server_error_message),
    ) {
        snackBarError(errorMessage)
    }

    fun showUnknownErrorAlert(
        errorMessage: String = getString(R.string.unknown_error_message),
    ) {
        snackBarError(errorMessage)
    }

    fun checkConnection() {
        val cm: ConnectivityManager = requireContext().applicationContext
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