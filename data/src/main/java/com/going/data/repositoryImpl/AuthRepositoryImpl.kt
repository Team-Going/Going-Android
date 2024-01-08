package com.going.data.repositoryImpl

import com.going.domain.GoingDataStore
import com.going.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val goingDataStore: GoingDataStore,
) : AuthRepository {
    override fun getAccessToken(): String = goingDataStore.accessToken

    override fun setAccessToken(accessToken: String) {
        goingDataStore.accessToken = accessToken
    }

    override fun getRefreshToken(): String = goingDataStore.refreshToken

    override fun setRefreshToken(refreshToken: String) {
        goingDataStore.refreshToken = refreshToken
    }
}
