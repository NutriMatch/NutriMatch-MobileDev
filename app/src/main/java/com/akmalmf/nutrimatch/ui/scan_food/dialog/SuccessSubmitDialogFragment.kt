package com.akmalmf.nutrimatch.ui.scan_food.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.akmalmf.nutrimatch.databinding.DialogSuccessSubmitBinding

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/10 15:20
 * akmalmf007@gmail.com
 */
class SuccessSubmitDialogFragment: DialogFragment()  {
    private var _binding: DialogSuccessSubmitBinding? = null
    private val binding get() = _binding!!

    var onOkClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSuccessSubmitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val title = arguments?.getString(EXTRA_TITLE) ?: ""
        if (title.isNotEmpty()) {
            binding.textTitle.text = title
        }

        val subTitle = arguments?.getString(EXTRA_SUB_TITLE) ?: ""
        if (subTitle.isNotEmpty()) {
            binding.textSubTitle.text = subTitle
        }
        binding.buttonClose.setOnClickListener {
            onOkClick()
            dismiss()
        }
    }

    companion object {
        const val EXTRA_TITLE= "title"
        const val EXTRA_SUB_TITLE= "subTitle"
        fun newInstance(title: String, subTitle: String): SuccessSubmitDialogFragment {
            val f = SuccessSubmitDialogFragment()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_SUB_TITLE, subTitle)
            f.arguments = args
            return f
        }
    }
}