package com.akmalmf.nutrimatch.ui.scan_food.nutritional_content

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseToolbarFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.databinding.FragmentNutritionalContentBinding
import com.akmalmf.nutrimatch.ui.scan_food.dialog.SuccessSubmitDialogFragment
import com.akmalmf.nutrimatch.utils.convertToFile
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible


class NutritionalContentFragment : BaseToolbarFragment<FragmentNutritionalContentBinding>() {
    override fun setToolbar(): Toolbar? {
        bi?.apply {
            textToolbar.text = getString(R.string.preview_capture)
        }
        return bi?.toolbar
    }

    override fun setToolbarBack(): Boolean {
        bi?.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        return true
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNutritionalContentBinding
        get() = FragmentNutritionalContentBinding::inflate

    private val viewModel: NutritionalContentViewModel by hiltNavGraphViewModels(R.id.scan_food_navigation)

    private val adapter by lazy { NutritionAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        val args: NutritionalContentFragmentArgs by navArgs()
        var nutritionContent: MutableList<ScanNutritionResponse> =
            args.nutritionContent.toMutableList()
        val uri = Uri.parse(args.uri)
        adapter.setItem(nutritionContent)
        bi?.apply {
            imagePreview.setImageURI(uri)
            rvNutrition.adapter = adapter
            imageAdd.setOnClickListener {
                val dialog = NutritionBottomSheetFragment.newInstance()
                dialog.show(childFragmentManager, tag)
                dialog.setListener(object :
                    NutritionBottomSheetFragment.NutritionBottomSheetListener {
                    override fun onAddItem(item: ScanNutritionResponse) {
                        adapter.addSingleItem(item)
                        dialog.dismiss()
                    }

                    override fun onEditItem(item: ScanNutritionResponse) {
                    }

                })
            }

            buttonSubmit.setOnClickListener {
                if(nutritionContent.size > 0){
                    viewModel.submitFood(convertToFile(requireContext(), uri), nutritionContent).observe(requireActivity()){
                        when(it.status){
                            Status.LOADING -> {
                                progressBar.toVisible()
                                buttonSubmit.toInvisible()
                            }
                            Status.SUCCESS -> {
                                progressBar.toGone()
                                buttonSubmit.toVisible()
                                if(it.data != null){
                                    val dialog = SuccessSubmitDialogFragment.newInstance("Success!", it.data.message)
                                    dialog.show(childFragmentManager, tag)
                                    dialog.onOkClick = {
                                        requireActivity().finish()
                                    }
                                }
                            }
                            Status.ERROR -> {
                                progressBar.toGone()
                                buttonSubmit.toVisible()
                                it.message?.let { it1 -> snackBarError(it1) }
                            }
                        }
                    }
                } else {
                    snackBarError(getString(R.string.food_nutrition_required))
                }
            }
        }

        adapter.onItemEdit = {
            val dialog = NutritionBottomSheetFragment.newInstance(Bundle().apply {
                putParcelable(NutritionBottomSheetFragment.EDIT_DATA, it)
            })

            dialog.show(childFragmentManager, tag)
            dialog.setListener(object :
                NutritionBottomSheetFragment.NutritionBottomSheetListener {
                override fun onAddItem(item: ScanNutritionResponse) {
                    dialog.dismiss()
                }

                override fun onEditItem(item: ScanNutritionResponse) {
                    val pos = adapter.findItem(it)
                    adapter.editItem(item, pos)
                    nutritionContent = adapter.getItem()
                    dialog.dismiss()
                }

            })
        }
        adapter.onItemDelete = {
            adapter.removeItem(it)
            nutritionContent = adapter.getItem()
        }
    }

    override fun initObservable() {
    }
}