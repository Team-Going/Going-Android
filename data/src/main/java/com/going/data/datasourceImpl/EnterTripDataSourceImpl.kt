package com.going.data.datasourceImpl

import com.going.data.datasource.EnterTripDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.request.EnterTripRequestDto
import com.going.data.dto.request.StartInviteTripRequestDto
import com.going.data.dto.response.EnterTripResponseDto
import com.going.data.dto.response.StartInviteTripResponseDto
import com.going.data.service.EnterTripService
import javax.inject.Inject

class EnterTripDataSourceImpl @Inject constructor(
    private val enterTripService: EnterTripService,
) : EnterTripDataSource {
    override suspend fun postEnterTrip(
        code: EnterTripRequestDto
    ): BaseResponse<EnterTripResponseDto> =
        enterTripService.postEnterTrip(code)

    override suspend fun postStartInviteTrip(
        tripId: Long,
        request: StartInviteTripRequestDto
    ): BaseResponse<StartInviteTripResponseDto> =
        EnterTripService.postStartInviteTrip(tripId, request)
}
