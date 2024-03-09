package com.going.domain.repository

import com.going.domain.entity.request.EditTripRequestModel
import com.going.domain.entity.response.TripInfoModel

interface EditTripRepository {

    suspend fun getTripInfo(
        tripId: Long
    ): Result<TripInfoModel>
    suspend fun patchEditTripInfo(
        tripId: Long,
        request: EditTripRequestModel
    ): Result<Unit>
}