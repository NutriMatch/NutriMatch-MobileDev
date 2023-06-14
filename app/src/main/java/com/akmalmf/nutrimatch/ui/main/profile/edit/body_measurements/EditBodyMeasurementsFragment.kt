package com.akmalmf.nutrimatch.ui.main.profile.edit.body_measurements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseToolbarFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.core.domain.model.BodyMeasurement
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.databinding.FragmentEditBodyMeasurementsBinding
import com.akmalmf.nutrimatch.ui.main.profile.edit.EditProfileActivity
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.setText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible

class EditBodyMeasurementsFragment : BaseToolbarFragment<FragmentEditBodyMeasurementsBinding>() {
    private var activityLevel: String = ""
    private var gender: String = ""

    override fun setToolbar(): Toolbar? {
        bi?.apply {
            include.textToolbar.text = getString(R.string.edit_password)
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

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditBodyMeasurementsBinding
        get() = FragmentEditBodyMeasurementsBinding::inflate

    private val viewModel: EditBodyMeasurementsViewModel by hiltNavGraphViewModels(R.id.edit_profile_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        val bodyMeasurement: BodyMeasurement? =
            arguments?.getParcelable(EditProfileActivity.FLAG_EDIT_BODY_MEASUREMENTS)

        if (bodyMeasurement != null){
            bi?.apply {
                textInputHeight.setText(bodyMeasurement.height.toString())
                textInputWeight.setText(bodyMeasurement.weight.toString())
                gender = bodyMeasurement.gender
                if(gender.lowercase() == "m"){
                    radioMale.isChecked = true
                } else {
                    radioFemale.isChecked = true
                }

                activityLevel = bodyMeasurement.activityLevel
                if(activityLevel.lowercase() == "l"){
                    radioLow.isChecked = true
                    textDescriptionActivity.text = getString(R.string.low_activity_description)
                } else if(activityLevel.lowercase() == "m"){
                    radioMedium.isChecked = true
                    textDescriptionActivity.text = getString(R.string.medium_activity_description)
                } else {
                    radioHigh.isChecked = true
                    textDescriptionActivity.text = getString(R.string.high_activity_description)
                }


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
            }
        } else {
            requireActivity().finish()
        }

        bi?.apply {
            buttonSave.setOnClickListener {
                if (validateForm()) {
                    viewModel.editBodyMeasurements(textInputHeight.getText().toInt(), textInputWeight.getText().toInt(), gender, activityLevel).observe(requireActivity()){
                        when(it.status){
                            Status.LOADING -> {
                                progressBar.toVisible()
                                buttonSave.toInvisible()
                            }
                            Status.SUCCESS -> {
                                progressBar.toGone()
                                buttonSave.toVisible()
                                it.data?.let { it1 -> snackBarSuccess(it1.message) }
                                requireActivity().finish()
                            }
                            Status.ERROR -> {
                                progressBar.toGone()
                                buttonSave.toVisible()
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
            if(textInputHeight.getText().isEmpty()) textInputHeight.helperText = getString(R.string.height_input_required)
            if(textInputWeight.getText().isEmpty()) textInputHeight.helperText = getString(R.string.weight_input_required)
            if(gender.isEmpty()) snackBarError(getString(R.string.gender_input_required))
            if(activityLevel.isEmpty()) snackBarError(getString(R.string.activities_level_input_required))
            return textInputHeight.getText().isNotEmpty() && textInputWeight.getText().isNotEmpty() && gender.isNotEmpty() && activityLevel.isNotEmpty()
        }
        return false
    }

    override fun initObservable() {
    }

}