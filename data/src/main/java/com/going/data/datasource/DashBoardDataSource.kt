package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.DashBoardResponseDto

interface DashBoardDataSource {
    suspend fun getTripList(
        progress: String
    ): BaseResponse<List<DashBoardResponseDto>>
}