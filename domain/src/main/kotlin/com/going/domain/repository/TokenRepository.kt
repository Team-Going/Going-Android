package com.going.domain.repository

interface TokenRepository {
    fun getAccessToken(): String
    fun setTokens(accessToken: String, refreshToken: String)

    fun getRefreshToken(): String
}
