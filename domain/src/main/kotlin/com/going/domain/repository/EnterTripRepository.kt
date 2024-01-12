package com.going.domain.repository

import com.going.domain.entity.request.EnterTripRequestModel
import com.going.domain.entity.response.EnterTripModel

interface EnterTripRepository {
    suspend fun postEnterTrip(
        requestEnterTripModel: EnterTripRequestModel
    ): Result<EnterTripModel>
}
