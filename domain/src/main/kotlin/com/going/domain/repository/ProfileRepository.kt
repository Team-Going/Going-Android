package com.going.domain.repository

import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.request.PreferenceChangeRequestModel
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.domain.entity.response.UserProfileResponseModel

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfileResponseModel>

    suspend fun patchUserProfile(userProfileResponseModel: UserProfileRequestModel): Result<Unit>

    suspend fun getParticipantProfile(participantProfileRequestModel: ParticipantProfileRequestModel): Result<ParticipantProfileResponseModel>

    suspend fun patchPreferenceTag(
        tripId: Long,
        preferenceChangeRequestModel: PreferenceChangeRequestModel
    ): Result<Unit>
}
