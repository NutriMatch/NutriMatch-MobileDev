package com.akmalmf.nutrimatch.core.domain.usecase.master

import com.akmalmf.nutrimatch.abstraction.data.Resource
import com.akmalmf.nutrimatch.core.data.source.remote.response.BaseApiResponse
import com.akmalmf.nutrimatch.core.data.source.remote.response.master.DashboardResponse
import com.akmalmf.nutrimatch.core.domain.repository.MasterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/08 15:49
 * akmalmf007@gmail.com
 */
class GetDashboardUseCase @Inject constructor(
    private val repository: MasterRepository
) {
    operator fun invoke(): Flow<Resource<BaseApiResponse<DashboardResponse>>> {
        return flow{
            emit(Resource.loading())
            emit(repository.dashboard())
        }.flowOn(Dispatchers.IO)
    }
}