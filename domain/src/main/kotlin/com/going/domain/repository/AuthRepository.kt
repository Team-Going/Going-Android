package com.going.domain.repository

interface AuthRepository {
    fun getAccessToken(): String
    fun setAccessToken(accessToken: String)

    fun getRefreshToken(): String
    fun setRefreshToken(refreshToken: String)
}
