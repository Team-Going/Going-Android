package com.going.data.datasourceImpl

import com.going.data.datasource.StartInviteTripDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.request.StartInviteTripRequestDto
import com.going.data.dto.response.StartInviteTripResponseDto
import com.going.data.service.StartInviteTripService
import javax.inject.Inject

class StartInviteTripDataSourceImpl @Inject constructor(
    private val startInviteTripService: StartInviteTripService,
) : StartInviteTripDataSource {

    override suspend fun postStartInviteTrip(
        tripId: Long,
        request: StartInviteTripRequestDto
    ): BaseResponse<StartInviteTripResponseDto> =
        startInviteTripService.postStartInviteTrip(tripId, request)
}
