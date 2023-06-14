package com.akmalmf.nutrimatch.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.DashboardResponse
import com.akmalmf.nutrimatch.core.domain.usecase.master.GetDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardUseCase: GetDashboardUseCase
): ViewModel() {
    private val _dashboard = MutableLiveData<Resource<BaseApiResponse<DashboardResponse>>>()
    val dashboard: LiveData<Resource<BaseApiResponse<DashboardResponse>>>
        get() = _dashboard

    fun getDashboard(){
        getDashboardUseCase().onEach {
            _dashboard.postValue(it)
        }.launchIn(viewModelScope)
    }

}