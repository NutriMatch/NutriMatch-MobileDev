package com.akmalmf.nutrimatch.ui.scan_food.nutritional_content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseBottomSheetFragment
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.core.domain.model.NutritionInfoModel
import com.akmalmf.nutrimatch.databinding.BottomSheetNutritionBinding
import com.akmalmf.nutrimatch.utils.getText
import com.akmalmf.nutrimatch.utils.setText

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/10 09:33
 * akmalmf007@gmail.com
 */
class NutritionBottomSheetFragment : BaseBottomSheetFragment<BottomSheetNutritionBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetNutritionBinding
        get() = BottomSheetNutritionBinding::inflate

    private var listener: NutritionBottomSheetListener? = null

    override fun initView(savedInstanceState: Bundle?) {
        var editData: ScanNutritionResponse? = arguments?.getParcelable(EDIT_DATA)

        bi?.apply {
            if (editData != null) {
                textTitle.text = getString(R.string.edit_nutritional_content)
                buttonAdd.text = getString(R.string.edit)
                textInputName.setText(editData.foodTitle)
                textInputWeight.setText(editData.nutritionInfo.weight.toString())
                textInputProtein.setText(editData.nutritionInfo.protein.toString())
                textInputFat.setText(editData.nutritionInfo.fat.toString())
                textInputCarbs.setText(editData.nutritionInfo.carb.toString())
            }
            buttonAdd.setOnClickListener {
                if (validateForm()) {
                    val foodNutrition = NutritionInfoModel(
                        textInputWeight.getText().toDouble(),
                        null,
                        textInputProtein.getText().toDouble(),
                        textInputFat.getText().toDouble(),
                        textInputCarbs.getText().toDouble()
                    )
                    if(editData == null){
                        listener?.onAddItem(
                            ScanNutritionResponse(
                                textInputName.getText(),
                                foodNutrition
                            )
                        )
                    } else {
                        listener?.onEditItem(
                            ScanNutritionResponse(
                                textInputName.getText(),
                                foodNutrition
                            )
                        )
                    }

                }
            }
        }
    }

    private fun validateForm(): Boolean {
        bi?.apply {
            textInputName.helperText = null
            textInputWeight.helperText = null
            textInputProtein.helperText = null
            textInputFat.helperText = null
            textInputCarbs.helperText = null
            if (textInputName.getText().isEmpty()) textInputName.helperText =
                getString(R.string.food_name_input_required)
            if (textInputWeight.getText().isEmpty()) textInputWeight.helperText =
                getString(R.string.weight_input_required)
            if (textInputProtein.getText().isEmpty()) textInputProtein.helperText =
                getString(R.string.protein_input_required)
            if (textInputFat.getText().isEmpty()) textInputFat.helperText =
                getString(R.string.fat_input_required)
            if (textInputCarbs.getText().isEmpty()) textInputCarbs.helperText =
                getString(R.string.carbs_input_required)

            return textInputName.getText().isNotEmpty() && textInputWeight.getText()
                .isNotEmpty() && textInputProtein.getText().isNotEmpty() && textInputFat.getText()
                .isNotEmpty() && textInputCarbs.getText().isNotEmpty()
        }
        return false
    }

    override fun initObservable() {
    }


    fun setListener(listener: NutritionBottomSheetListener) {
        this.listener = listener
    }

    interface NutritionBottomSheetListener {
        fun onAddItem(
            item: ScanNutritionResponse
        )

        fun onEditItem(item: ScanNutritionResponse)
    }

    companion object {
        const val EDIT_DATA = "edit"
        fun newInstance(args: Bundle): NutritionBottomSheetFragment {
            val f = NutritionBottomSheetFragment()
            f.arguments = args
            return f
        }

        fun newInstance(): NutritionBottomSheetFragment {
            return NutritionBottomSheetFragment()
        }
    }
}