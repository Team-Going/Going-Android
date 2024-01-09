package com.going.domain.repository

import com.going.domain.entity.response.AuthTokenModel

interface SignInRepository {
    suspend fun postSignIn(
        Authorization: String,
        platform: String,
    ): Result<AuthTokenModel>
}
