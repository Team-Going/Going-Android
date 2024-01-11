package com.going.data.datasourceImpl

import com.going.data.datasource.DashBoardDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.response.DashBoardResponseDto
import com.going.data.service.DashBoardService
import javax.inject.Inject

class DashBoardDataSourceImpl @Inject constructor(
    private val dashBoardService: DashBoardService
) : DashBoardDataSource {
    override suspend fun getTripList(progress: String): BaseResponse<List<DashBoardResponseDto>> =
        dashBoardService.getTripList(progress)
}