package com.akmalmf.nutrimatch.ui.main.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseRecyclerViewAdapter
import com.akmalmf.nutrimatch.core.domain.model.DetailHistoryFoodModel
import com.akmalmf.nutrimatch.databinding.ItemHistoryFoodBinding

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 16:45
 * akmalmf007@gmail.com
 */
class HistoryFoodAdapter : BaseRecyclerViewAdapter<HistoryFoodAdapter.VHolder, DetailHistoryFoodModel>() {
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryFoodBinding.bind(itemView)

        fun onBind(it: DetailHistoryFoodModel) {
            binding.apply {
                binding.imageFood.load(it.imageUrl) {
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_error)
                }
                binding.textTitle.text = it.title
                textCalories.text = "${it.nutritionInfo.calories} kcal"
                textProtein.text = it.nutritionInfo.protein.toString()
                textFat.text = it.nutritionInfo.fat.toString()
                textCarbs.text = it.nutritionInfo.carb.toString()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history_food, parent, false))

    override fun onBindViewHolder(holder: VHolder, item: DetailHistoryFoodModel, position: Int) {
        holder.onBind(item)
    }

    override fun getItemCount() = items.size
}