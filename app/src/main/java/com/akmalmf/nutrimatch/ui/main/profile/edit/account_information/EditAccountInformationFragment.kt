package com.akmalmf.nutrimatch.ui.main.profile.edit.account_information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseToolbarFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.core.domain.model.BodyMeasurement
import com.akmalmf.nutrimatch.core.domain.model.UserModel
import com.akmalmf.nutrimatch.databinding.FragmentEditAccountInformationBinding
import com.akmalmf.nutrimatch.ui.main.profile.edit.EditProfileActivity
import com.akmalmf.nutrimatch.utils.convertToDate
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.setText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Locale

class EditAccountInformationFragment : BaseToolbarFragment<FragmentEditAccountInformationBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditAccountInformationBinding
        get() = FragmentEditAccountInformationBinding::inflate

    override fun setToolbar(): Toolbar? {
        bi?.apply {
            include.textToolbar.text = getString(R.string.edit_account_information)
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

    private val viewModel: EditAccountInformationViewModel by hiltNavGraphViewModels(R.id.edit_profile_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        val user: UserModel? =
            arguments?.getParcelable(EditProfileActivity.FLAG_EDIT_ACCOUNT_INFORMATION)
        if(user != null){
            bi?.apply {
                textInputBirthday.editText?.setOnClickListener {
                    try {
                        val constrainBuilder: CalendarConstraints.Builder = CalendarConstraints.Builder()
                        val fifteenYearAgo: Calendar = Calendar.getInstance()
                        fifteenYearAgo.add(Calendar.YEAR, -12)
                        constrainBuilder.setValidator(DateValidatorPointBackward.before(fifteenYearAgo.timeInMillis))
                        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                        datePickerBuilder.setSelection(fifteenYearAgo.timeInMillis)
                        datePickerBuilder.setCalendarConstraints(constrainBuilder.build())
                        val materialDatePicker = datePickerBuilder.build()
                        materialDatePicker.show(parentFragmentManager, "Birthday")
                        materialDatePicker.addOnPositiveButtonClickListener {
                            textInputBirthday.setText(it.convertToDate(Locale.getDefault()))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                textInputFullname.setText(user.fullname)
                textInputBirthday.setText(user.birthday)
                buttonSave.setOnClickListener {
                    if (validateForm()){
                        viewModel.editAccountInformation(textInputFullname.getText(), textInputBirthday.getText()).observe(requireActivity()){
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
        } else {
            requireActivity().finish()
        }
    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputFullname.helperText = null
            textInputBirthday.helperText = null
            if(textInputFullname.getText().isEmpty()) textInputFullname.helperText = getString(R.string.fullname_input_required)
            if(textInputBirthday.getText().isEmpty()) textInputBirthday.helperText = getString(R.string.birthday_input_required)

            return textInputFullname.getText().isNotEmpty() && textInputBirthday.getText().isNotEmpty()
        }
        return false
    }

    override fun initObservable() {
    }
}