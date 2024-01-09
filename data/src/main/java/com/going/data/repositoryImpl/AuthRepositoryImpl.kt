package com.going.data.repositoryImpl

import com.going.data.local.GoingDataStore
import com.going.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val goingDataStore: GoingDataStore,
) : AuthRepository {
    override fun getAccessToken(): String = goingDataStore.accessToken

    override fun setTokens(accessToken: String, refreshToken: String) {
        goingDataStore.accessToken = accessToken
        goingDataStore.refreshToken = refreshToken
    }

    override fun getRefreshToken(): String = goingDataStore.refreshToken
}
