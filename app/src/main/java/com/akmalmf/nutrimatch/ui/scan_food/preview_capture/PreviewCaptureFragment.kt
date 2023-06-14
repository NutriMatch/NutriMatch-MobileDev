package com.akmalmf.nutrimatch.ui.scan_food.preview_capture

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseToolbarFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentPreviewCaptureBinding
import com.akmalmf.nutrimatch.ui.scan_food.ScanFoodActivity
import com.akmalmf.nutrimatch.ui.scan_food.dialog.AskSubmitManualDialog
import com.akmalmf.nutrimatch.utils.convertToFile
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible


class PreviewCaptureFragment : BaseToolbarFragment<FragmentPreviewCaptureBinding>() {
    override fun setToolbar(): Toolbar? {
        bi?.apply {
            include.textToolbar.text = getString(R.string.preview_capture)
        }
        return bi?.include?.toolbar
    }

    override fun setToolbarBack(): Boolean {
        bi?.apply {
            include.toolbar.setNavigationOnClickListener {
                requireActivity().finish()
            }
        }
        return true
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPreviewCaptureBinding
        get() = FragmentPreviewCaptureBinding::inflate

    private val viewModel: PreviewCaptureViewModel by hiltNavGraphViewModels(R.id.scan_food_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        val uriString: String? =
            arguments?.getString(ScanFoodActivity.FLAG_URI)
        if (uriString != null) {
            val uri = Uri.parse(uriString)
            bi?.apply {
                imagePreview.setImageURI(uri)
                buttonSubmit.setOnClickListener {
                    if (validateForm()) {
                        viewModel.scanNutrition(
                            convertToFile(requireContext(), uri),
                            textInputFoodWeight.getText().toInt()
                        ).observe(requireActivity()) {
                            when (it.status) {
                                Status.LOADING -> {
                                    progressBar.toVisible()
                                    buttonSubmit.toInvisible()
                                }

                                Status.SUCCESS -> {
                                    progressBar.toGone()
                                    buttonSubmit.toVisible()
                                    if (it.data?.data != null) {
                                        val action =
                                            PreviewCaptureFragmentDirections.actionPreviewCaptureFragmentToNutritionalContentFragment(
                                                it.data.data.toTypedArray(),
                                                uri.toString()
                                            )
                                        findNavController().navigate(action)
                                    }
                                }

                                Status.ERROR -> {
                                    progressBar.toGone()
                                    buttonSubmit.toVisible()
                                    if(it.data?.message != null){
                                        val dialog = AskSubmitManualDialog.newInstance()
                                        dialog.show(childFragmentManager, tag)
                                        dialog.onNoClick = {
                                            requireActivity().finish()
                                        }
                                        dialog.onYesClick = {
                                            findNavController().navigate(PreviewCaptureFragmentDirections.actionPreviewCaptureFragmentToSubmitManualFragment(uri.toString()))
                                        }

                                    } else {
                                        showErrorAlert(it.cause, it.data?.message ?: it.message)
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } else {
            requireActivity().finish()
        }
    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputFoodWeight.helperText = null
            if (textInputFoodWeight.getText().isEmpty()) textInputFoodWeight.helperText =
                getString(R.string.weight_input_required)

            return textInputFoodWeight.getText().isNotEmpty()
        }
        return false
    }

    override fun initObservable() {
    }
}