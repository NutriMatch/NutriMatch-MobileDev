package com.akmalmf.nutrimatch.ui.auth.body_measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentBodyMeasurementBinding
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible

class BodyMeasurementFragment : BaseFragment<FragmentBodyMeasurementBinding>() {

    var gender = ""
    var activityLevel = ""
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBodyMeasurementBinding
        get() = FragmentBodyMeasurementBinding::inflate

    val viewModel: BodyMeasurementViewModel by hiltNavGraphViewModels(R.id.auth_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        val args: BodyMeasurementFragmentArgs by navArgs()
        bi?.apply {
            radioMale.setOnClickListener {
                gender = "M"
            }

            radioFemale.setOnClickListener {
                gender = "F"
            }

            radioLow.setOnClickListener {
                activityLevel = "L"
                textDescriptionActivity.text = getString(R.string.low_activity_description)
            }
            radioMedium.setOnClickListener {
                activityLevel = "M"
                textDescriptionActivity.text = getString(R.string.medium_activity_description)
            }
            radioHigh.setOnClickListener {
                activityLevel = "H"
                textDescriptionActivity.text = getString(R.string.high_activity_description)
            }
            buttonNext.setOnClickListener {
                if (validateForm()) {
                    viewModel.register(
                        args.fullname,
                        args.birthday,
                        args.email,
                        args.password,
                        textInputHeight.getText().toInt(),
                        textInputWeight.getText().toInt(),
                        gender,
                        activityLevel
                    ).observe(requireActivity()) {
                        when (it.status) {
                            Status.LOADING -> {
                                progressBar.toVisible()
                                buttonNext.toInvisible()
                            }

                            Status.SUCCESS -> {
                                progressBar.toGone()
                                buttonNext.toVisible()
                                findNavController().navigate(BodyMeasurementFragmentDirections.actionBodyMeasurementFragmentToSuccessRegisterFragment())
                            }

                            Status.ERROR -> {
                                progressBar.toGone()
                                buttonNext.toVisible()
                                showErrorAlert(it.cause, it.data?.message ?: it.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputHeight.helperText = null
            textInputWeight.helperText = null
            if (textInputHeight.isEmpty()) textInputHeight.helperText =
                getString(R.string.height_input_required)
            if (textInputWeight.isEmpty()) textInputWeight.helperText =
                getString(R.string.weight_input_required)
            if (gender.isEmpty()) snackBarError(getString(R.string.gender_input_required))
            if (activityLevel.isEmpty()) snackBarError(getString(R.string.activities_level_input_required))

            return textInputHeight.getText().isNotEmpty() && textInputWeight.getText()
                .isNotEmpty() && gender.isNotEmpty() && activityLevel.isNotEmpty()
        }
        return false
    }

    override fun initObservable() {

    }

}