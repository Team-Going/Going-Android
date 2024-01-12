package com.going.domain.repository

import com.going.domain.entity.request.RequestEnterTripModel
import com.going.domain.entity.response.EnterTripModel

interface EnterTripRepository {

    suspend fun postEnterTrip(
        requestEnterTripModel: RequestEnterTripModel
    ): Result<EnterTripModel?>
}
