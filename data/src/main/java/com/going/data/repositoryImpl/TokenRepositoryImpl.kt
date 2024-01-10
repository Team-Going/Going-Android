package com.going.data.repositoryImpl

import com.going.data.local.GoingDataStore
import com.going.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val goingDataStore: GoingDataStore,
) : TokenRepository {
    override fun getAccessToken(): String = goingDataStore.accessToken
    override fun getRefreshToken(): String = goingDataStore.refreshToken

    override fun setTokens(accessToken: String, refreshToken: String) {
        goingDataStore.accessToken = accessToken
        goingDataStore.refreshToken = refreshToken
    }

    override fun clearTokens() {
        goingDataStore.accessToken = ""
        goingDataStore.refreshToken = ""
    }
}
