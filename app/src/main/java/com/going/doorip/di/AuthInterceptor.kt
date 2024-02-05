package com.going.doorip.di

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.going.data.local.GoingDataStore
import com.going.domain.entity.request.TokenReissueRequestModel
import com.going.domain.repository.TokenReissueRepository
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.ui.extension.toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenReissueRepository: TokenReissueRepository,
    private val dataStore: GoingDataStore,
    @ApplicationContext private val context: Context,
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
                            authRequest.newBuilder().removeHeader(AUTHORIZATION).newAuthBuilder()
                                .build()

                        return chain.proceed(newRequest)
                    }
                } catch (t: Throwable) {
                    Timber.d(t.message)
                }

                dataStore.clearInfo()

                Handler(Looper.getMainLooper()).post {
                    context.toast(TOKEN_EXPIRED_ERROR)
                    Intent(context, SignInActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(this)
                    }
                }
            }
        }
        return response
    }

    private fun Request.Builder.newAuthBuilder() =
        this.addHeader(AUTHORIZATION, "$BEARER ${dataStore.accessToken}")

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val TOKEN_EXPIRED_ERROR = "토큰이 만료되었어요\n다시 로그인 해주세요"
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}
