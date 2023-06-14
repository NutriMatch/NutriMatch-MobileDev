package com.akmalmf.nutrimatch.ui.auth.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseRecyclerViewAdapter
import com.akmalmf.nutrimatch.core.domain.model.OnBoardingModel
import com.akmalmf.nutrimatch.databinding.ItemOnBoardingBinding

class OnBoardingAdapter : BaseRecyclerViewAdapter<OnBoardingAdapter.VHolder, OnBoardingModel>() {

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOnBoardingBinding.bind(itemView)

        fun onBind(it: OnBoardingModel) {
            binding.textTitle.text = it.title
            binding.textSubTitle.text = it.subTitle
            binding.image.load(it.image) {
                placeholder(R.drawable.ic_image_placeholder)
                error(R.drawable.ic_image_error)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_on_boarding, parent, false))

    override fun onBindViewHolder(holder: VHolder, item: OnBoardingModel, position: Int) {
        holder.onBind(item)
    }

    override fun getItemCount() = items.size
}