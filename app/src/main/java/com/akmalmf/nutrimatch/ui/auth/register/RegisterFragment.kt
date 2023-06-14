package com.akmalmf.nutrimatch.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentRegisterBinding
import com.akmalmf.nutrimatch.utils.convertToDate
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.isValidEmail
import com.akmalmf.nutrimatch.utils.setText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Locale

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.auth_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        bi?.apply {
            textInputBirthday.editText?.setOnClickListener {
                try {
                    val constrainBuilder: CalendarConstraints.Builder =
                        CalendarConstraints.Builder()
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
            buttonRegister.setOnClickListener {
                if (validateForm()) {
                    viewModel.checkEmail(textInputEmail.getText()).observe(requireActivity()) {
                        when (it.status) {
                            Status.LOADING -> {
                                progressBar.toVisible()
                                buttonRegister.toInvisible()
                            }

                            Status.SUCCESS -> {
                                progressBar.toGone()
                                buttonRegister.toVisible()
                                findNavController().navigate(
                                    RegisterFragmentDirections.actionRegisterFragmentToBodyMeasurementFragment(
                                        textInputFullname.getText(),
                                        textInputBirthday.getText(),
                                        textInputEmail.getText(),
                                        textInputPasword.getText()
                                    )
                                )
                            }

                            Status.ERROR -> {
                                progressBar.toGone()
                                buttonRegister.toVisible()
                                showErrorAlert(it.cause, it.data?.message ?: it.message)
                            }
                        }
                    }
                    //
                }
            }
            buttonBack.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToOnBoardingFragment())
            }
        }
    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputFullname.helperText = null
            textInputEmail.helperText = null
            textInputBirthday.helperText = null
            textInputPasword.helperText = null
            if (!isValidEmail(textInputEmail.getText())) textInputEmail.helperText =
                getString(R.string.invalid_email_helper)
            if (textInputFullname.getText().isEmpty()) textInputFullname.helperText =
                getString(R.string.fullname_input_required)
            if (textInputEmail.getText().isEmpty()) textInputEmail.helperText =
                getString(R.string.email_filed_helper)
            if (textInputBirthday.getText().isEmpty()) textInputBirthday.helperText =
                getString(R.string.birthday_input_required)
            if (textInputPasword.getText().isEmpty()) textInputPasword.helperText =
                getString(R.string.password_filed_helper)

            return textInputFullname.getText().isNotEmpty() && textInputEmail.getText()
                .isNotEmpty() && textInputBirthday.getText()
                .isNotEmpty() && textInputPasword.getText().isNotEmpty() && isValidEmail(
                textInputEmail.getText()
            )
        }
        return false
    }

    override fun initObservable() {

    }
}