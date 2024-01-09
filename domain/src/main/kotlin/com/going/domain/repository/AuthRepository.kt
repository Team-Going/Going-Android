package com.going.domain.repository

import com.going.domain.entity.response.AuthTokenModel

interface AuthRepository {
    suspend fun postSignIn(
        Authorization: String,
        platform: String,
    ): Result<AuthTokenModel>

    suspend fun postSignUp(
        name: String,
        intro: String,
        platform: String,
    ): Result<AuthTokenModel>
}
