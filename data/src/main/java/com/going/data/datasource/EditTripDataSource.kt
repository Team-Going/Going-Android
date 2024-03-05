package com.going.data.datasource

import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.EditTripInfoRequestDto
import retrofit2.http.Body
import retrofit2.http.Path

interface EditTripDataSource {
    suspend fun patchEditTripInfo(
        @Path("tripId") tripId: Long,
        @Body request: EditTripInfoRequestDto
    ): NonDataBaseResponse
}