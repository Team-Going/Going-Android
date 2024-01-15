package com.going.data.interceptor

import com.going.data.local.GoingDataStore
import com.going.domain.entity.request.TokenReissueRequestModel
import com.going.domain.repository.TokenReissueRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenReissueRepository: TokenReissueRepository,
    private val dataStore: GoingDataStore,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        Timber.d("GET ACCESS TOKEN : ${dataStore.accessToken}")

        val authRequest = if (dataStore.accessToken.isNotBlank()) {
            originalRequest.newBuilder().newAuthBuilder().build()
        } else {
            originalRequest
        }

        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    runBlocking {
                        tokenReissueRepository.postReissueTokens(
                            dataStore.refreshToken,
                            TokenReissueRequestModel(dataStore.userId),
                        )
                    }.onSuccess { data ->
                        dataStore.apply {
                            accessToken = data.accessToken
                            refreshToken = data.refreshToken
                            userId = data.userId
                        }

                        response.close()

                        val newRequest =
                            authRequest.newBuilder().removeHeader("Authorization").newAuthBuilder()
                                .build()

                        return chain.proceed(newRequest)
                    }.onFailure {
                        dataStore.clearInfo()
                    }

                    dataStore.clearInfo()

                    // refreshToken 만료 처리를 위한 리프레시 토큰 만료 코드 포함 리스폰스 리턴
                    return response
                } catch (t: Throwable) {
                    dataStore.clearInfo()

                    Timber.e(t)
                }
            }
        }
        return response
    }

    private fun Request.Builder.newAuthBuilder() =
        this.addHeader(AUTHORIZATION, "$BEARER ${dataStore.accessToken}")

    companion object {
        private const val CODE_TOKEN_EXPIRED = 201
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}
