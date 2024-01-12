package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.StartInviteTripRequestDto
import com.going.data.dto.response.StartInviteTripResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface StartInviteTripService {
    @POST("/api/trips/{tripId}/entry")
    suspend fun postStartInviteTrip(
        @Body request: StartInviteTripRequestDto,
    ): BaseResponse<StartInviteTripResponseDto>
}
