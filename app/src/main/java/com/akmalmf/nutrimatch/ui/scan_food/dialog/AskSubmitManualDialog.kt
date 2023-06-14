package com.akmalmf.nutrimatch.ui.scan_food.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.akmalmf.nutrimatch.databinding.DialogAskSubmitManualBinding

class AskSubmitManualDialog : DialogFragment()  {
    private var _binding: DialogAskSubmitManualBinding? = null
    private val binding get() = _binding!!

    var onYesClick: () -> Unit = {}

    var onNoClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAskSubmitManualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.buttonNo.setOnClickListener {
            onNoClick()
            dismiss()
        }

        binding.buttonYes.setOnClickListener {
            onYesClick()
            dismiss()
        }
    }

    companion object {
        fun newInstance(): AskSubmitManualDialog {
            return AskSubmitManualDialog()
        }
    }
}