package com.going.data.datasourceImpl

import com.going.data.datasource.ProfileDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.request.ParticipantProfileRequestDto
import com.going.data.dto.response.ParticipantProfileResponseDto
import com.going.data.dto.response.UserProfileResponseDto
import com.going.data.service.ProfileService
import com.going.domain.entity.request.ParticipantProfileRequestModel
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileService: ProfileService,
) : ProfileDataSource {
    override suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto> =
        profileService.getUserProfile()

    override suspend fun patchUserProfile(): BaseResponse<UserProfileResponseDto> =
        profileService.patchUserProfile()

    override suspend fun getParticipantProfile(participantProfileRequestDto: ParticipantProfileRequestDto): BaseResponse<ParticipantProfileResponseDto> =
        profileService.getParticipantProfile(participantProfileRequestDto.participantId)
}
