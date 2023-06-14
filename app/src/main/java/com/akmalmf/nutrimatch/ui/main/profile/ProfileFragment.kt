package com.akmalmf.nutrimatch.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.nutrimatch.R
import com.akmalmf.nutrimatch.abstraction.base.BaseFragment
import com.akmalmf.nutrimatch.abstraction.data.Status
import com.akmalmf.nutrimatch.databinding.FragmentProfileBinding
import com.akmalmf.nutrimatch.ui.auth.AuthActivity
import com.akmalmf.nutrimatch.ui.main.profile.edit.EditProfileActivity
import com.akmalmf.nutrimatch.utils.formatToDate
import com.akmalmf.nutrimatch.utils.toActivitiesLevel
import com.akmalmf.nutrimatch.utils.toGenderClassification
import com.akmalmf.nutrimatch.utils.toGone
import com.akmalmf.nutrimatch.utils.toInvisible
import com.akmalmf.nutrimatch.utils.toVisible

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
    private val viewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    override fun initView(savedInstanceState: Bundle?) {
        bi?.apply {
            swipeRefresh.setOnRefreshListener {
                viewModel.getProfile()
            }

            layoutLogout.setOnClickListener {
                viewModel.logout()
                AuthActivity.start(requireContext())
                requireActivity().finishAffinity()
            }
            imageEditPassword.setOnClickListener {
                EditProfileActivity.start(requireContext(), EditProfileActivity.FLAG_EDIT_PASSWORD)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    override fun initObservable() {
        viewModel.profile.observe(requireActivity()) {
            when (it.status) {
                Status.LOADING -> {
                    startShimmer()
                }

                Status.SUCCESS -> {
                    if (it.data?.data != null) {
                        val user = it.data.data
                        val body = user.bodyMeasurement
                        bi?.apply {
                            textEmail.text = user.email
                            textEmailAccount.text = user.email

                            textFullname.text = user.fullname
                            textFullnameAccounts.text = user.fullname

                            textBirthdayAccount.text = user.birthday.formatToDate()

                            textHeightBody.text = "${body.height} cm"

                            textWeightBody.text = "${body.weight} kg"

                            textActivityLevelBody.text = body.activityLevel.toActivitiesLevel()


                            textGender.text = body.gender.toGenderClassification()
                            textGenderBody.text = body.gender.toGenderClassification()
                            swipeRefresh.isRefreshing = false

                            imageEditAccountInformation.setOnClickListener {
                                EditProfileActivity.start(requireContext(), Bundle().apply {
                                    putParcelable(
                                        EditProfileActivity.FLAG_EDIT_ACCOUNT_INFORMATION,
                                        user
                                    )
                                }, EditProfileActivity.FLAG_EDIT_ACCOUNT_INFORMATION)
                            }
                            imageEditBodyMeasurements.setOnClickListener {
                                EditProfileActivity.start(
                                    requireContext(),
                                    Bundle().apply {
                                        putParcelable(
                                            EditProfileActivity.FLAG_EDIT_BODY_MEASUREMENTS,
                                            user.bodyMeasurement
                                        )
                                    },
                                    EditProfileActivity.FLAG_EDIT_BODY_MEASUREMENTS
                                )
                            }
                        }

                    }
                    stopShimmer()
                }

                Status.ERROR -> {
                    startShimmer()
                    bi?.apply {
                        swipeRefresh.isRefreshing = false
                    }
                    showErrorAlert(it.cause, it.data?.message ?: it.message)
                }
            }
        }
    }

    private fun startShimmer() {
        bi?.apply {
            groupHeader.toInvisible()
            groupAccountInformation.toInvisible()
            groupBodyMeasurements.toInvisible()

            shimmerHeader.toVisible()
            shimmerHeader.startShimmer()
            shimmerAccountInformation.toVisible()
            shimmerAccountInformation.startShimmer()
            shimmerBodyMeasurements.toVisible()
            shimmerBodyMeasurements.startShimmer()
        }
    }

    private fun stopShimmer() {
        bi?.apply {
            groupHeader.toVisible()
            groupAccountInformation.toVisible()
            groupBodyMeasurements.toVisible()

            shimmerHeader.toGone()
            shimmerHeader.stopShimmer()
            shimmerAccountInformation.toGone()
            shimmerAccountInformation.stopShimmer()
            shimmerBodyMeasurements.toGone()
            shimmerBodyMeasurements.stopShimmer()
        }
    }

}