package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.EnterTripRequestDto
import com.going.data.dto.response.EnterTripResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface EnterTripService {
    @POST("/api/trips/verify")
    suspend fun postEnterTrip(
        @Body body: EnterTripRequestDto,
    ): BaseResponse<EnterTripResponseDto>
}
