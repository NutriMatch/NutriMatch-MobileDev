package com.akmalmf.nutrimatch.ui.auth.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.databinding.FragmentOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import kotlin.concurrent.timerTask

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnBoardingBinding
        get() = FragmentOnBoardingBinding::inflate

    private val viewModel: OnBoardingViewModel by viewModels<OnBoardingViewModel>()
    private val adapter by lazy { OnBoardingAdapter() }

    private var currentPage: Int = 0
    private var imageCount = 0
    private var swipeTimer = Timer()

    override fun initView(savedInstanceState: Bundle?) {
        adapter.setItem(viewModel.getOnBoardingSlide())
        bi?.vPager?.adapter = adapter
        setupIndicator()
        setCurrentIndicator(0)
        setAutoSwipeImage()

        bi?.vPager?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                setCurrentIndicator(position)
            }
        })

        bi?.buttonLogin?.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
        }
        bi?.buttonRegister?.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToRegisterFragment())
        }
    }

    override fun onPause() {
        super.onPause()
        swipeTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        swipeTimer.cancel()
    }

    override fun initObservable() {

    }

    private fun setupIndicator() {
        val indicator = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicator.indices) {
            indicator[i] = ImageView(requireContext())

            indicator[i].apply {
                this?.isSelected = false
                this?.layoutParams = layoutParams
            }
            bi?.indicatorContainer?.addView(indicator[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = bi?.indicatorContainer?.childCount
        for (i in 0 until childCount!!) {
            val imageView = bi?.indicatorContainer?.get(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.indicator_selector
                )
            )
            imageView.isSelected = i == index
        }
    }

    private fun setAutoSwipeImage() {
        imageCount = adapter.itemCount

        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (currentPage == imageCount) {
                currentPage = 0
            }
            bi?.vPager?.setCurrentItem(currentPage++, true)
        }
        swipeTimer = Timer()
        swipeTimer.schedule(timerTask { handler.post(update) }, 5000, 5000)
    }

}