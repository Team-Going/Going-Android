package com.going.domain.repository

import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.entity.response.ParticipantProfileResponseModel

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfileRequestModel>

    suspend fun patchUserProfile(): Result<UserProfileRequestModel>

    suspend fun getParticipantProfile(participantProfileRequestModel: ParticipantProfileRequestModel): Result<ParticipantProfileResponseModel>
}
