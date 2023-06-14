package com.akmalmf.nutrimatch.ui.main.scan_food

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio.RATIO_4_3
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.databinding.FragmentScanFoodBinding
import com.akmalmf.nutrimatch.ui.auth.login.LoginViewModel
import com.akmalmf.nutrimatch.ui.scan_food.ScanFoodActivity
import com.akmalmf.nutrimatch.utils.convertToFile
import com.akmalmf.nutrimatch.utils.makeTempFile
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible

class ScanFoodFragment : BaseFragment<FragmentScanFoodBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScanFoodBinding
        get() = FragmentScanFoodBinding::inflate

    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.auth_navigation)

    private var camSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imgCapture: ImageCapture? = null

    override fun initView(savedInstanceState: Bundle?) {
        if (!allPermissionsGranted()) {
            bi?.apply {
                noAccessGroup.toVisible()
                cameraView.toInvisible()
            }
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        bi?.apply {
            buttonTakePicture.setOnClickListener {
                buttonTakePicture.isEnabled = false
                captureImage()
            }
            imageGalery.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            ScanFoodActivity.start(requireContext(), selectedImg)
        }
    }

    override fun initObservable() {
    }

    override fun onResume() {
        super.onResume()
        openCamera()
    }

    private fun openCamera() {
        val futureProvider = ProcessCameraProvider.getInstance(requireContext())
        futureProvider.addListener({
            imgCapture = ImageCapture.Builder().build()

            val provider = futureProvider.get()
            val preview = Preview.Builder()
                .setTargetAspectRatio(RATIO_4_3)
                .build().apply {
                    setSurfaceProvider(bi?.cameraView?.surfaceProvider)
                }

            try {
                provider.apply {
                    unbindAll()
                    bindToLifecycle(requireActivity(), camSelector, preview, imgCapture)
                }
            } catch (exc: Exception) {
                onCameraError()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun onCameraError() {
        bi?.apply {
            noAccessGroup.toVisible()
            cameraView.toInvisible()
        }
    }

    private fun captureImage() {

        val toCapture = imgCapture ?: return
        val file = makeTempFile(requireContext())
        val outputOption = ImageCapture.OutputFileOptions.Builder(file).build()

        toCapture.takePicture(outputOption,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    bi?.apply {
                        buttonTakePicture.isEnabled = true
                    }

                    onCameraError()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    bi?.apply {
                        buttonTakePicture.isEnabled = true
                    }
                    output.savedUri?.let { ScanFoodActivity.start(requireContext(), it) }
                }
            }
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                bi?.apply {
                    noAccessGroup.toVisible()
                    cameraView.toInvisible()
                }
                snackBarError("Permission Denied")
            } else {
                bi?.apply {
                    noAccessGroup.toGone()
                    cameraView.toVisible()
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}