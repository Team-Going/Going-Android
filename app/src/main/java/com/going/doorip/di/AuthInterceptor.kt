package com.going.doorip.di

import com.going.data.dto.BaseResponse
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.TokenRepository
import com.going.doorip.BuildConfig.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val tokenRepository: TokenRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        Timber.d("GET ACCESS TOKEN : ${tokenRepository.getAccessToken()}")

        val authRequest = if (tokenRepository.getAccessToken()
                .isNotBlank()
        ) {
            originalRequest.newAuthBuilder().build()
        } else {
            originalRequest
        }
        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    // refreshToken을 얻기 위해서 인터셉터 내부에서 직접적으로 통신을 하는게 맞을까?
                    // app 레이어에서 data layer에 직접 접근하는 것도 맘에 안들고, 직접 통신하는게 아키텍쳐 구조를 헤친다는 생각이 듦
                    // 속도를 측정해보고 repository를 사용한 통신방법이 속도가 느리지 않다면
                    // 추후 repository를 활용한 통신으로 수정할 예정
                    // 앱잼 기간내에 수정하는게 목표라 주석을 남겨두겠습니다. 수정 못할거 같으면 주석만 지울게용
                    val refreshTokenRequest = originalRequest.newBuilder().post("".toRequestBody())
                        .url("$BASE_URL/api/users/reissue")
                        .addHeader(
                            AUTHORIZATION,
                            tokenRepository.getRefreshToken(),
                        )
                        .build()
                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)
                    Timber.d("GET REFRESH TOKEN : $refreshTokenResponse")

                    if (refreshTokenResponse.isSuccessful) {
                        val responseToken = json.decodeFromString(
                            refreshTokenResponse.body?.string().toString(),
                        ) as BaseResponse<AuthTokenModel>

                        tokenRepository.setTokens(
                            accessToken = responseToken.data.accessToken,
                            refreshToken = responseToken.data.refreshToken,
                        )
                        refreshTokenResponse.close()
                        val newRequest = originalRequest.newAuthBuilder().build()
                        return chain.proceed(newRequest)
                    }

                    tokenRepository.clearTokens()

                    // refreshToken 만료 처리를 위한 리프레시 토큰 만료 코드 포함 리스폰스 리턴
                    return refreshTokenResponse
                } catch (t: Throwable) {
                    tokenRepository.clearTokens()
                    Timber.e(t)
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(AUTHORIZATION, "$BEARER ${tokenRepository.getAccessToken()}")

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}
