package com.going.data.datasource;

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.EnterTripRequestDto
import com.going.data.dto.response.EnterTripResponseDto
import retrofit2.http.Body

interface EnterTripDataSource {
    suspend fun postEnterTrip(
        @Body request: EnterTripRequestDto,
    ): BaseResponse<EnterTripResponseDto>
}
