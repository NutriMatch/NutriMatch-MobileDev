package com.akmalmf.nutrimatch.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentLoginBinding
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.isValidEmail
import com.akmalmf.nutrimatch.utils.onSubmit
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.auth_navigation)
    override fun initView(savedInstanceState: Bundle?) {
        bi?.buttonLogin?.setOnClickListener {
            submit()
        }
        bi?.buttonBack?.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToOnBoardingFragment())
        }


        bi?.textInputPasword?.editText?.onSubmit {
            submit()
        }
    }

    private fun submit() {
        if(validateForm()){
            viewModel.login(bi!!.textInputEmail.getText(), bi!!.textInputPasword.getText()).observe(requireActivity()){
                when(it.status){
                    Status.LOADING -> {
                        bi?.apply {
                            buttonLogin.toInvisible()
                            progressBar.toVisible()
                        }
                    }

                    Status.SUCCESS -> {
                        bi?.apply {
                            buttonLogin.toVisible()
                            progressBar.toGone()
                        }
                        it.data?.let { it1 -> it1.data?.let { it2 -> viewModel.saveUser(it2) } }
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
                        requireActivity().finishAffinity()
                    }

                    Status.ERROR -> {
                        bi?.apply {
                            buttonLogin.toVisible()
                            progressBar.toGone()
                        }
                        (it.data?.message ?: it.message)?.let { it1 -> snackBarError(it1) }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        if(!isValidEmail(bi!!.textInputEmail.getText())) bi!!.textInputEmail.helperText = getString(R.string.invalid_email_helper)
        if(bi!!.textInputEmail.getText().isEmpty()) bi!!.textInputEmail.helperText = getString(R.string.email_filed_helper)
        if(bi!!.textInputPasword.getText().isEmpty()) bi!!.textInputPasword.helperText = getString(R.string.password_filed_helper)
        return bi!!.textInputEmail.getText().isNotEmpty() && bi!!.textInputPasword.getText().isNotEmpty() && isValidEmail(bi!!.textInputEmail.getText())
    }

    override fun initObservable() {

    }

}