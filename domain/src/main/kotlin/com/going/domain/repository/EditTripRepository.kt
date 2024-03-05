package com.going.domain.repository

import com.going.domain.entity.request.EditTripRequestModel

interface EditTripRepository {
    suspend fun postEditTripInfo(
        tripId: Long,
        request: EditTripRequestModel
    ): Result<Unit>
}