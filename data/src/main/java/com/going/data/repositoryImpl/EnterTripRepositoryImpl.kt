package com.going.data.repositoryImpl

import com.going.data.datasource.EnterTripDataSource
import com.going.data.dto.request.toEnterPreferenceRequestDto
import com.going.data.dto.request.toEnterTripRequestDto
import com.going.data.dto.request.toStartInviteTripRequestDto
import com.going.domain.entity.request.EnterPreferenceRequestModel
import com.going.domain.entity.request.EnterTripRequestModel
import com.going.domain.entity.request.StartInviteTripRequestModel
import com.going.domain.entity.response.EnterPreferenceModel
import com.going.domain.entity.response.EnterTripModel
import com.going.domain.entity.response.StartInviteTripModel
import com.going.domain.repository.EnterTripRepository
import javax.inject.Inject

class EnterTripRepositoryImpl @Inject constructor(
    private val enterTripDataSource: EnterTripDataSource,
) : EnterTripRepository {

    override suspend fun postEnterTrip(
        requestEnterTripModel: EnterTripRequestModel
    ): Result<EnterTripModel> =
        runCatching {
            enterTripDataSource.postEnterTrip(
                requestEnterTripModel.toEnterTripRequestDto(),
            ).data.toEnterTripModel()
        }

    override suspend fun postStartInviteTrip(
        tripId: Long, requestStartInviteTripModel: StartInviteTripRequestModel
    ): Result<StartInviteTripModel> =
        runCatching {
            enterTripDataSource.postStartInviteTrip(
                tripId, requestStartInviteTripModel.toStartInviteTripRequestDto(),
            ).data.toStartInviteTripModel()
        }

    override suspend fun postEnterPreferenceTrip(
        request: EnterPreferenceRequestModel
    ): Result<EnterPreferenceModel> =
        runCatching {
            enterTripDataSource.postEnterPreferenceTrip(
                request.toEnterPreferenceRequestDto(),
            ).data.toEnterPreferenceModel()
        }
}
