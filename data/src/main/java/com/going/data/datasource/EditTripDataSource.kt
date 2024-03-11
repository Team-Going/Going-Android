package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.EditTripInfoRequestDto
import com.going.data.dto.response.TripInfoResponseDto

interface EditTripDataSource {
    suspend fun getTripInfo(
        tripId: Long
    ): BaseResponse<TripInfoResponseDto>

    suspend fun patchEditTripInfo(
        tripId: Long,
        request: EditTripInfoRequestDto
    ): NonDataBaseResponse

    suspend fun patchQuitTrip(
        tripId: Long
    ): NonDataBaseResponse

}