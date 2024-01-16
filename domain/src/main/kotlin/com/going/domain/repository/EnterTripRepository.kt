package com.going.domain.repository

import com.going.domain.entity.request.EnterPreferenceRequestModel
import com.going.domain.entity.request.EnterTripRequestModel
import com.going.domain.entity.request.StartInviteTripRequestModel
import com.going.domain.entity.response.EnterPreferenceModel
import com.going.domain.entity.response.EnterTripModel
import com.going.domain.entity.response.StartInviteTripModel

interface EnterTripRepository {
    suspend fun postEnterTrip(
        request: EnterTripRequestModel
    ): Result<EnterTripModel>

    suspend fun postStartInviteTrip(
        tripId: Long,
        request: StartInviteTripRequestModel
    ): Result<StartInviteTripModel>

    suspend fun postEnterPreferenceTrip(
        request: EnterPreferenceRequestModel
    ): Result<EnterPreferenceModel>
}
