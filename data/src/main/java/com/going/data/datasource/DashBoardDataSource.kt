package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.DashBoardResponseDto
import com.going.domain.entity.response.DashBoardModel

interface DashBoardDataSource {
    suspend fun getTripList(
        progress: String
    ): BaseResponse<DashBoardResponseDto>
}