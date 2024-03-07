package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.ParticipantProfileRequestDto
import com.going.data.dto.response.ParticipantProfileResponseDto
import com.going.data.dto.response.UserProfileResponseDto
import com.going.domain.entity.request.ParticipantProfileRequestModel

interface ProfileDataSource {
    suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto>

    suspend fun patchUserProfile(): BaseResponse<UserProfileResponseDto>

    suspend fun getParticipantProfile(
        participantProfileRequestModel: ParticipantProfileRequestDto
    ): BaseResponse<ParticipantProfileResponseDto>
}
