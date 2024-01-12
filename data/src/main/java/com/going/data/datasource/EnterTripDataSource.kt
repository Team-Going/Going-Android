package com.going.data.datasource;

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.EnterTripRequestDto
import com.going.data.dto.response.EnterTripResponseDto

interface EnterTripDataSource {
    suspend fun postEnterTrip(
        code: EnterTripRequestDto,
    ): BaseResponse<EnterTripResponseDto>
}
