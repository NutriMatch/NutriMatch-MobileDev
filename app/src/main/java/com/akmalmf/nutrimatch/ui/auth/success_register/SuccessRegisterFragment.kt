package com.akmalmf.nutrimatch.ui.auth.success_register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.databinding.FragmentSuccessRegisterBinding

class SuccessRegisterFragment : BaseFragment<FragmentSuccessRegisterBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSuccessRegisterBinding
        get() = FragmentSuccessRegisterBinding::inflate

    override fun initView(savedInstanceState: Bundle?) {
        bi?.apply {
            buttonLogin.setOnClickListener {
                findNavController().navigate(SuccessRegisterFragmentDirections.actionSuccessRegisterFragmentToLoginFragment())
            }
        }
    }
    override fun initObservable() {

    }

}