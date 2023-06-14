package com.akmalmf.nutrimatch.ui.auth.onboarding

import androidx.lifecycle.ViewModel
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.core.domain.model.OnBoardingModel


class OnBoardingViewModel : ViewModel() {
    fun getOnBoardingSlide() = mutableListOf(
        OnBoardingModel(
            R.drawable.image_onboarding1,
            "Easy Food Intake Monitoring",
            "You can easily record and track your daily food intake. NutriMatch makes it easy to monitor the calories, carbohydrates, protein, fat and other nutrients you consume. Maintain your nutritional balance effectively."
        ),
        OnBoardingModel(
            R.drawable.image_onboarding2,
            "Personalized Nutrition \nRecommendations",
            "NutriMatch will provide nutritional recommendations tailored to your individual needs. Using the data you provide, the app will help you achieve your nutritional goals."
        ),
        OnBoardingModel(
            R.drawable.image_onboarding3,
            "Useful Information",
            "NutriMatch provides interactive features that will help you achieve nutritional balance. Get useful information on food nutritional values and portion recommendations. With these features, you can make better food choices and improve your healthy lifestyle."
        )
    )
}