package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.StartInviteTripRequestDto
import com.going.data.dto.response.StartInviteTripResponseDto
import retrofit2.http.Body

interface StartInviteTripDataSource {

    suspend fun postStartInviteTrip(
        @Body request: StartInviteTripRequestDto,
    ): BaseResponse<StartInviteTripResponseDto>
}
