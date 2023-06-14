package com.akmalmf.nutrimatch.ui.scan_food.submit_manual

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isNotEmpty
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseToolbarFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentSubmitManualBinding
import com.akmalmf.nutrimatch.ui.scan_food.dialog.SuccessSubmitDialogFragment
import com.akmalmf.nutrimatch.utils.convertToFile
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible

class SubmitManualFragment : BaseToolbarFragment<FragmentSubmitManualBinding>() {
    private val viewModel: SubmitManualViewModel by hiltNavGraphViewModels(R.id.scan_food_navigation)
    private val args: SubmitManualFragmentArgs by navArgs()


    override fun setToolbar(): Toolbar? {
        bi?.apply {
            include.textToolbar.text = getString(R.string.input_manually)
        }
        return bi?.include?.toolbar
    }

    override fun setToolbarBack(): Boolean {
        bi?.apply {
            include.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        return true
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSubmitManualBinding
        get() = FragmentSubmitManualBinding::inflate

    override fun initView(savedInstanceState: Bundle?) {
        val uri = Uri.parse(args.uri)
        bi?.apply {
            imagePreview.setImageURI(uri)
            buttonSubmit.setOnClickListener {
                if (validateForm()) {
                    viewModel.submit(
                        convertToFile(requireContext(), uri),
                        textInputFoodName.getText(),
                        textInputFoodWeight.getText().toDouble(),
                        textInputFoodCalories.getText().toInt()
                    ).observe(requireActivity()) {
                        when (it.status) {
                            Status.LOADING -> {
                                progressBar.toVisible()
                                buttonSubmit.toInvisible()
                            }

                            Status.SUCCESS -> {
                                progressBar.toGone()
                                buttonSubmit.toVisible()
                                if (it.data != null) {
                                    val dialog = SuccessSubmitDialogFragment.newInstance(
                                        "Success!",
                                        it.data.message
                                    )
                                    dialog.show(childFragmentManager, tag)
                                    dialog.onOkClick = {
                                        requireActivity().finish()
                                    }
                                }
                            }

                            Status.ERROR -> {
                                progressBar.toGone()
                                buttonSubmit.toVisible()
                                (it.data?.message ?: it.message)?.let { it1 -> showErrorAlert(it.cause, it1) }
                            }
                        }
                    }
                }
            }
        }

    }

    override fun initObservable() {

    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputFoodName.helperText = null
            textInputFoodWeight.helperText = null
            textInputFoodCalories.helperText = null

            if (textInputFoodName.getText().isEmpty()) textInputFoodName.helperText =
                getString(R.string.food_name_input_required)
            if (textInputFoodWeight.getText().isEmpty()) textInputFoodWeight.helperText =
                getString(R.string.weight_input_required)
            if (textInputFoodCalories.getText().isEmpty()) textInputFoodCalories.helperText =
                getString(R.string.calories_input_required)

            return textInputFoodName.getText().isNotEmpty() && textInputFoodWeight.getText()
                .isNotEmpty() && textInputFoodCalories.isNotEmpty()
        }
        return false
    }


}