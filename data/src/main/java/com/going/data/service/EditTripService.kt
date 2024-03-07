package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.EditTripInfoRequestDto
import com.going.data.dto.response.TripInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditTripService {

    @GET("api/trips/{tripId}")
    suspend fun getTripInfo(
        @Path("tripId") tripId: Long
    ): BaseResponse<TripInfoResponseDto>

    @PATCH("api/trips/{tripId}")
    suspend fun patchEditTripInfo(
        @Path("tripId") tripId: Long,
        @Body request: EditTripInfoRequestDto,
    ): NonDataBaseResponse
}