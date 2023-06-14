package com.akmalmf.nutrimatch.ui.main.profile.edit.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.abstraction.base.BaseToolbarFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentEditPasswordBinding
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible


class EditPasswordFragment : BaseToolbarFragment<FragmentEditPasswordBinding>() {
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

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditPasswordBinding
        get() = FragmentEditPasswordBinding::inflate

    private val viewModel: EditPasswordViewModel by hiltNavGraphViewModels(R.id.edit_profile_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        bi?.apply {
            buttonSave.setOnClickListener {
                if (validateForm()) {
                    viewModel.editPassword(
                        textInputPasword.getText(),
                        textInputNewPasword.getText()
                    ).observe(requireActivity()) {
                        when (it.status) {
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
                                (it.data?.message ?: it.message)?.let { it1 -> snackBarError(it1) }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputPasword.helperText = null
            textInputNewPasword.helperText = null
            if (textInputPasword.getText().isEmpty()) textInputPasword.helperText =
                getString(R.string.password_filed_helper)
            if (textInputNewPasword.getText().isEmpty()) textInputNewPasword.helperText =
                getString(R.string.password_filed_helper)

            return textInputPasword.getText().isNotEmpty() && textInputNewPasword.getText()
                .isNotEmpty()
        }
        return false
    }

    override fun initObservable() {
    }


}