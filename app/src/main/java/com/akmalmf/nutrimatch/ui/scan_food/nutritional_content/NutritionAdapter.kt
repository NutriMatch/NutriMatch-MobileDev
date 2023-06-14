package com.akmalmf.nutrimatch.ui.scan_food.nutritional_content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseRecyclerViewAdapter
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.ScanNutritionResponse
import com.akmalmf.nutrimatch.databinding.ItemNutritionBinding


/**
 * Created by Akmal Muhamad Firdaus on 2023/06/10 06:15
 * akmalmf007@gmail.com
 */
class NutritionAdapter :
    BaseRecyclerViewAdapter<NutritionAdapter.VHolder, ScanNutritionResponse>() {
    var onItemDelete: ((ScanNutritionResponse) -> Unit)? = null
    var onItemEdit: ((ScanNutritionResponse) -> Unit)? = null
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNutritionBinding.bind(itemView)
        fun onBind(data: ScanNutritionResponse) {
            binding.apply {
                textTitle.text = data.foodTitle
                textWeightFood.text = "${data.nutritionInfo.weight}g"
                textProtein.text = "${data.nutritionInfo.protein}g"
                textCarbs.text = "${data.nutritionInfo.carb}g"
                textFat.text = "${data.nutritionInfo.fat}g"
                imageOptions.setOnClickListener {
                    val popupMenu = PopupMenu(itemView.context, itemView)
                    popupMenu.inflate(R.menu.popup_options)
                    popupMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.textEdit -> {
                                onItemEdit?.invoke(data)
                            }
                            R.id.textDelete -> {
                                onItemDelete?.invoke(data)
                            }
                        }

                        false
                    }
                    popupMenu.show()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_nutrition, parent, false))

    override fun onBindViewHolder(holder: VHolder, item: ScanNutritionResponse, position: Int) {
        holder.onBind(item)
    }

    override fun getItemCount() = items.size
}