package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.DashBoardResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DashBoardService {

    @GET("api/trips")
    suspend fun getTripList(
        @Query("progress") progress: String
    ) : BaseResponse<DashBoardResponseDto>

}