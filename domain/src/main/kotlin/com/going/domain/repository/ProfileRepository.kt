package com.going.domain.repository

import com.going.domain.entity.request.UserProfileRequestModel

interface ProfileRepository {
    suspend fun getUserProfile(): Result<UserProfileRequestModel>
}
