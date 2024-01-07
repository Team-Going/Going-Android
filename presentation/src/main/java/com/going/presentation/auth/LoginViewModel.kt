package com.going.presentation.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.LoginRepository
import com.going.ui.extension.UiState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {
    private val _postChangeTokenState = MutableStateFlow<UiState<AuthTokenModel>>(UiState.Empty)
    val postChangeTokenState: StateFlow<UiState<AuthTokenModel?>> = _postChangeTokenState

    private val _isAppLoginAvailable = MutableStateFlow(true)
    val isAppLoginAvailable: StateFlow<Boolean> = _isAppLoginAvailable

    private var webLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Log.e("TAG", "error : $error / token : $token")
        if (error == null && token != null) {
            changeTokenFromServer(
                accessToken = token.accessToken,
            )
        }
    }

    private var appLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            // 뒤로가기 경우 예외 처리
            Log.e("TAG", "error : $error / token : $token")
            if (!(error is ClientError && error.reason == ClientErrorCause.Cancelled)) {
                Log.e("TAG", "이 무슨 error : $error / token : $token")
                _isAppLoginAvailable.value = false
            }
        } else if (token != null) {
            Log.e("TAG", "전부 error : $error / token : $token")
            Log.e("TAG", "${token.accessToken}")
            changeTokenFromServer(
                accessToken = token.accessToken,
            )
        }
    }

    fun startKakaoLogIn(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context) && isAppLoginAvailable.value) {
            Log.e("TAG", "startKakaoLogIn: app")
            UserApiClient.instance.loginWithKakaoTalk(
                context = context,
                callback = appLoginCallback,
            )
        } else {
            Log.e("TAG", "startKakaoLogIn: web")
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
            // 통신 로직
            loginRepository.postSignin(accessToken, social).onSuccess {
                // 성공시 서버에서 준 정보를 넣는 예시 코드
                Timber.e("성공고오고오고공")
                _postChangeTokenState.value = UiState.Success(
                    AuthTokenModel(
                        accessToken = "testAccessToekn",
                        refreshToken = "testRefreshToekn",
                    ),
                )
            }.onFailure { err ->
                Log.e("TAG", "changeTokenFromServer: ${err.message}", )
                Timber.e("실패패패패패패")
            }
        }
    }

    companion object {
        const val KAKAO = "kakao"
    }
}
