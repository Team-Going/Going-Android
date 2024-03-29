package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.ParticipantProfileRequestDto
import com.going.data.dto.request.PreferenceChangeRequestDto
import com.going.data.dto.request.UserProfileRequestDto
import com.going.data.dto.response.ParticipantProfileResponseDto
import com.going.data.dto.response.UserProfileResponseDto
import com.going.domain.entity.request.PreferenceChangeRequestModel
import retrofit2.http.Body

interface ProfileDataSource {
    suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto>

    suspend fun patchUserProfile(
        @Body userProfileResponseModel: UserProfileRequestDto
    ): NonDataBaseResponse

    suspend fun getParticipantProfile(
        participantProfileRequestModel: ParticipantProfileRequestDto
    ): BaseResponse<ParticipantProfileResponseDto>

    suspend fun patchPreferenceTag(
        tripId: Long,
        request: PreferenceChangeRequestDto
    ): NonDataBaseResponse
}
