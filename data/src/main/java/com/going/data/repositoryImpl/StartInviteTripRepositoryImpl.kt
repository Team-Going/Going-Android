package com.going.data.repositoryImpl

import com.going.data.datasource.StartInviteTripDataSource
import com.going.data.dto.request.toStartInviteTripRequestDto
import com.going.domain.entity.request.StartInviteTripRequestModel
import com.going.domain.entity.response.StartInviteTripModel
import com.going.domain.repository.StartInviteTripRepository
import javax.inject.Inject

class StartInviteTripRepositoryImpl @Inject constructor(
    private val startInviteTripDataSource: StartInviteTripDataSource
) : StartInviteTripRepository {

    override suspend fun postStartInviteTrip(
        requestStartInviteTripModel: StartInviteTripRequestModel
    ): Result<StartInviteTripModel> =
        runCatching {
            startInviteTripDataSource.postStartInviteTrip(
                requestStartInviteTripModel.toStartInviteTripRequestDto(),
            ).data.toStartInviteTripModel()
        }
}

