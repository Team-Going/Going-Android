package com.going.going.di

import android.content.Context
import com.going.data.dto.BaseResponse
import com.going.data.local.GoingDataStore
import com.going.domain.entity.response.AuthTokenModel
import com.going.going.BuildConfig.BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val dataStore: GoingDataStore,
    @ApplicationContext private val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = if (dataStore.accessToken != "") {
            originalRequest.newAuthBuilder().build()
        } else {
            originalRequest
        }
        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    val refreshTokenRequest = originalRequest.newBuilder().post("".toRequestBody())
                        .url("$BASE_URL/api/v1/auth/token/issue")
                        .addHeader(AUTHORIZATION, dataStore.refreshToken)
                        .build()
                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)
                    Timber.d("GET REFRESH TOKEN : $refreshTokenResponse")

                    if (refreshTokenResponse.isSuccessful) {
                        val responseToken = json.decodeFromString(
                            refreshTokenResponse.body?.string().toString(),
                        ) as BaseResponse<AuthTokenModel>

                        with(dataStore) {
                            accessToken = responseToken.data.accessToken
                            refreshToken = responseToken.data.refreshToken
                        }
                        refreshTokenResponse.close()
                        val newRequest = originalRequest.newAuthBuilder().build()
                        return chain.proceed(newRequest)
                    }

                    with(dataStore) {
                        accessToken = ""
                        refreshToken = ""
                    }
                } catch (t: Throwable) {
                    Timber.e(t)
                    with(dataStore) {
                        accessToken = ""
                        refreshToken = ""
                    }
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(AUTHORIZATION, "${dataStore.accessToken}")

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val AUTHORIZATION = "Authorization"
    }
}
