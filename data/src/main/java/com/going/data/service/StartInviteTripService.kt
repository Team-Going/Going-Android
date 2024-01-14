package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.StartInviteTripRequestDto
import com.going.data.dto.response.StartInviteTripResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface StartInviteTripService {
    @POST("/api/trips/{tripId}/entry")
    suspend fun postStartInviteTrip(
        @Path("tripId") tripId: Long,
        @Body request: StartInviteTripRequestDto,
    ): BaseResponse<StartInviteTripResponseDto>
}
