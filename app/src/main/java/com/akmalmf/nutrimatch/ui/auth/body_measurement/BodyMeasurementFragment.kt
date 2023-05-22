package com.akmalmf.nutrimatch.ui.auth.body_measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.databinding.FragmentBodyMeasurementBinding

class BodyMeasurementFragment : BaseFragment<FragmentBodyMeasurementBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBodyMeasurementBinding
        get() = FragmentBodyMeasurementBinding::inflate

    override fun initView(savedInstanceState: Bundle?) {
        bi.buttonNext.setOnClickListener {
            findNavController().navigate(BodyMeasurementFragmentDirections.actionBodyMeasurementFragmentToMainActivity())
        }
    }

    override fun initObservable() {

    }

}