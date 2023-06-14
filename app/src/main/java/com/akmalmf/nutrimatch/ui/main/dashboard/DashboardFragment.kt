package com.akmalmf.nutrimatch.ui.main.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModelProvider
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentHomeBinding
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.max

class DashboardFragment :  BaseFragment<FragmentHomeBinding>() {

    private val breakfastAdapter by lazy { HistoryFoodAdapter() }

    private val lunchAdapter by lazy { HistoryFoodAdapter() }

    private val dinnerAdapter by lazy { HistoryFoodAdapter() }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private val viewModel: DashboardViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.getDashboard()
        bi?.swipeRefresh?.setOnRefreshListener {
            viewModel.getDashboard()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initObservable() {
        viewModel.dashboard.observe(this){
            when(it.status){
                Status.LOADING -> {
                    startShimmer()
                }
                Status.SUCCESS -> {
                    stopShimmer()
                    bi?.apply {
                        swipeRefresh.isRefreshing = false
                    }
                    if(it.data?.data != null){
                        val response = it.data.data
                        val userModel = response.user
                        val graph = response.graph
                        val history = response.historyFood
                        bi?.apply {
                            val currentDate = LocalDate.now()
                            val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
                            textDate.text = currentDate.format(formatter)

                            textFullname.text = userModel.fullname
                            textEmail.text = userModel.email
                            val currentCalories = (graph.calories.target - graph.calories.current).toInt()
                            if(currentCalories <= 0){
                                snackBarWarning(getString(R.string.reached_calories_target))
                            }
                            textCurrentCalories.text = max(currentCalories, 0).toString()
                            progressCalories.progress = ((graph.calories.current / graph.calories.target) * 100).toInt()

                            textCurrentProtein.text = graph.protein.current.toString()
                            textTargetProtein.text = "/${graph.protein.target}g"
                            progressProtein.progress = ((graph.protein.current / graph.protein.target) * 100).toInt()

                            textCurrentFat.text = graph.fat.current.toString()
                            textTargetFat.text = "/${graph.fat.target}g"
                            progressFat.progress = ((graph.fat.current / graph.fat.target) * 100).toInt()

                            textCurrentCarbs.text = graph.carbs.current.toString()
                            textTargetCarbs.text = "/${graph.carbs.target}g"
                            progressCarbs.progress = ((graph.carbs.current / graph.carbs.target) * 100).toInt()

                            if(history.breakfast.isNotEmpty()){
                                breakfastAdapter.setItem(history.breakfast.toMutableList())
                                rvBreakfast.adapter = breakfastAdapter
                            } else {
                                rvBreakfast.toInvisible()
                                nodataBreakfast.toVisible()
                            }

                            if(history.lunch.isNotEmpty()){
                                lunchAdapter.setItem(history.lunch.toMutableList())
                                rvLunch.adapter = lunchAdapter
                            } else {
                                rvLunch.toInvisible()
                                nodataLunch.toVisible()
                            }

                            if(history.dinner.isNotEmpty()){
                                dinnerAdapter.setItem(history.dinner.toMutableList())
                                rvDinner.adapter = dinnerAdapter
                            } else {
                                rvDinner.toInvisible()
                                nodataDinner.toVisible()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    stopShimmer()
                    bi?.apply {
                        swipeRefresh.isRefreshing = false
                    }
                    (it.data?.message ?: it.message).let {data ->
                        if (data != null) {
                            snackBarError(data)
                        }
                    }

                }
            }
        }
    }

    private fun startShimmer() {
        bi?.apply {
            shimmerGraph.startShimmer()
            shimmerGraph.toVisible()

            cardGraph.toInvisible()

            shimmerBreakfast.startShimmer()
            shimmerBreakfast.toVisible()
            shimmerLunch.startShimmer()
            shimmerLunch.toVisible()
            shimmerDinner.startShimmer()
            shimmerDinner.toVisible()

            nodataDinner.toGone()
            nodataLunch.toGone()
            nodataBreakfast.toGone()

            rvDinner.toGone()
            rvLunch.toGone()
            rvBreakfast.toGone()
        }
    }

    private fun stopShimmer() {
        bi?.apply {
            shimmerGraph.stopShimmer()
            shimmerGraph.toGone()

            cardGraph.toVisible()

            shimmerBreakfast.stopShimmer()
            shimmerBreakfast.toGone()
            shimmerLunch.stopShimmer()
            shimmerLunch.toGone()
            shimmerDinner.stopShimmer()
            shimmerDinner.toGone()

            nodataDinner.toGone()
            nodataLunch.toGone()
            nodataBreakfast.toGone()

            rvDinner.toVisible()
            rvLunch.toVisible()
            rvBreakfast.toVisible()
        }
    }
}