package com.going.data.service

import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.EditTripInfoRequestDto
import com.going.data.dto.request.StartInviteTripRequestDto
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditTripService {
    @PATCH("/api/trips/{tripId}")
    suspend fun patchEditTripInfo(
        @Path("tripId") tripId: Long,
        @Body request: EditTripInfoRequestDto,
    ): NonDataBaseResponse
}