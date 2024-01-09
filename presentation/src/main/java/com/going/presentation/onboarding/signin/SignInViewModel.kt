package com.going.presentation.onboarding.signin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.TokenRepository
import com.going.presentation.util.toErrorCode
import com.going.ui.extension.UiState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    private val _postChangeTokenState = MutableStateFlow<UiState<AuthTokenModel>>(UiState.Empty)
    val postChangeTokenState: StateFlow<UiState<AuthTokenModel?>> = _postChangeTokenState

    private val _isAppLoginAvailable = MutableStateFlow(true)
    val isAppLoginAvailable: StateFlow<Boolean> = _isAppLoginAvailable

    private var webLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error == null && token != null) {
            changeTokenFromServer(
                accessToken = token.accessToken,
            )
        }
    }

    private var appLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            // 뒤로가기 경우 예외 처리
            if (!(error is ClientError && error.reason == ClientErrorCause.Cancelled)) {
                _isAppLoginAvailable.value = false
            }
        } else if (token != null) {
            changeTokenFromServer(
                accessToken = token.accessToken,
            )
        }
    }

    fun startKakaoLogIn(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context) && isAppLoginAvailable.value) {
            UserApiClient.instance.loginWithKakaoTalk(
                context = context,
                callback = appLoginCallback,
            )
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = webLoginCallback,
            )
        }
    }

    // 서버통신 - 카카오 토큰 보내서 서비스 토큰 받아오기 - 서버와 협의 후 수정예정
    private fun changeTokenFromServer(
        accessToken: String,
        social: String = KAKAO,
    ) {
        _postChangeTokenState.value = UiState.Loading

        viewModelScope.launch {
            authRepository.postSignIn(accessToken, social).onSuccess {
                tokenRepository.setTokens(it.accessToken, it.refreshToken)

                _postChangeTokenState.value = UiState.Success(
                    AuthTokenModel(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                    ),
                )
            }.onFailure {
                val errorCode = toErrorCode(it)

                _postChangeTokenState.value = UiState.Failure(errorCode)
            }
        }
    }

    companion object {
        const val KAKAO = "kakao"
    }
}
