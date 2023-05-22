package com.akmalmf.nutrimatch.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun initView(savedInstanceState: Bundle?) {
        bi.buttonLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        }
        bi.buttonBack.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToOnBoardingFragment())
        }
    }

    override fun initObservable() {

    }

}