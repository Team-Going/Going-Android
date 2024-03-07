package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.EditTripInfoRequestDto
import com.going.data.dto.response.TripInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.Path

interface EditTripDataSource {
    suspend fun patchEditTripInfo(
        @Path("tripId") tripId: Long,
        @Body request: EditTripInfoRequestDto
    ): NonDataBaseResponse

    suspend fun getTripInfo(
        tripId: Long
    ): BaseResponse<TripInfoResponseDto>
}