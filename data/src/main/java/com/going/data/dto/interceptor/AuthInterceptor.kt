package com.going.data.interceptor

import com.going.data.BuildConfig.BASE_URL
import com.going.data.dto.BaseResponse
import com.going.data.local.GoingDataStore
import com.going.domain.entity.response.AuthTokenModel
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
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        Timber.d("GET ACCESS TOKEN : ${dataStore.accessToken}")

        val authRequest = if (dataStore.accessToken.isNotBlank()) {
            originalRequest.newAuthBuilder().build()
        } else {
            originalRequest
        }

        //val authRequest = originalRequest.newAuthBuilder().build()

        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    // refreshToken을 얻기 위해서 인터셉터 내부에서 직접적으로 통신을 하는게 맞을까?
                    // 직접 통신하는게 기존의 통신방법과 차이가 있기 때문에 통일성을 해친다는 생각이 듦
                    // 그리고 이렇게 되면 Base Url이 App모듈에도 필요하고, Data모듈에도 필요함
                    // 그래서 지금 생각으론 Retrofit 관련 모듈이 data 모듈에 있어야 할 것 같음
                    // 앱잼 기간내에 수정하는게 목표라 주석을 남겨두겠습니다. 수정 못할거 같으면 주석만 지울게용
                    val refreshTokenRequest = originalRequest.newBuilder().post("".toRequestBody())
                        .url("$BASE_URL/api/users/reissue")
                        .addHeader(
                            AUTHORIZATION,
                            dataStore.refreshToken,
                        )
                        .build()
                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)
                    Timber.d("GET REFRESH TOKEN : $refreshTokenResponse")

                    if (refreshTokenResponse.isSuccessful) {
                        val responseToken = json.decodeFromString(
                            refreshTokenResponse.body?.string().toString(),
                        ) as BaseResponse<AuthTokenModel>

                        dataStore.apply {
                            accessToken = responseToken.data.accessToken
                            refreshToken = responseToken.data.refreshToken
                        }

                        refreshTokenResponse.close()

                        val newRequest = originalRequest.newAuthBuilder().build()
                        return chain.proceed(newRequest)
                    }

                    dataStore.clearInfo()

                    // refreshToken 만료 처리를 위한 리프레시 토큰 만료 코드 포함 리스폰스 리턴
                    return refreshTokenResponse
                } catch (t: Throwable) {
                    dataStore.clearInfo()

                    Timber.e(t)
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(AUTHORIZATION, "$BEARER ${dataStore.accessToken}")
        //this.newBuilder().addHeader(AUTHORIZATION, "$BEARER eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0MCIsImlhdCI6MTcwNTI1NjkwNCwiZXhwIjoxNzA1ODYxNzA0fQ.m8yWjfOb3e_tL1o_GuuwPU7ZUpNjFhKKKBWLRQHv0qc")

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}
