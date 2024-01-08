package com.going.domain.repository

import com.going.domain.entity.response.AuthTokenModel

interface LoginRepository {
    suspend fun postSignin(
        Authorization: String,
        platform: String,
    ): Result<AuthTokenModel>
}
