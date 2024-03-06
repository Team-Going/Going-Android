package com.going.domain.repository

import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.request.UserProfileRequestModel

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfileRequestModel>

    suspend fun getParticipantProfile(): Result<ParticipantProfileRequestModel>
}
