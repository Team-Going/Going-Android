package com.going.domain.repository

interface AuthRepository {
    fun getAccessToken(): String
    fun setTokens(accessToken: String, refreshToken: String)

    fun getRefreshToken(): String
}
