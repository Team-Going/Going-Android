package com.going.domain.repository

import com.going.domain.entity.request.EnterTripRequestModel
import com.going.domain.entity.request.StartInviteTripRequestModel
import com.going.domain.entity.response.EnterTripModel
import com.going.domain.entity.response.StartInviteTripModel

interface EnterTripRepository {
    suspend fun postEnterTrip(
        requestEnterTripModel: EnterTripRequestModel
    ): Result<EnterTripModel>

    suspend fun postStartInviteTrip(
        tripId: Long,
        requestStartInviteTripModel: StartInviteTripRequestModel
    ): Result<StartInviteTripModel>
}
