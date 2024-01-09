package com.going.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.SignInRepository
import com.going.ui.extension.UiState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginRepository: SignInRepository,
) : ViewModel() {
    private val _postChangeTokenState = MutableStateFlow<UiState<AuthTokenModel>>(UiState.Empty)
    val postChangeTokenState: StateFlow<UiState<AuthTokenModel?>> = _postChangeTokenState

    private val _isAppLoginAvailable = MutableStateFlow(true)
    val isAppLoginAvailable: StateFlow<Boolean> = _isAppLoginAvailable

    private val _isMoveAvailable = MutableStateFlow(true)
    val isMoveAvailable: StateFlow<Boolean> = _isMoveAvailable

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
            // 통신 로직
            loginRepository.postSignIn(accessToken, social).onSuccess {
                // 이때 받은 토큰은 JWT가 아니기 때문에 Shared에 저장하지 않는다. -> Intent로 온보딩으로 옮기고 사용
                _postChangeTokenState.value = UiState.Success(
                    AuthTokenModel(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                    ),
                )
            }.onFailure {
                if (it is retrofit2.HttpException) {
                    val jsonTemp = it.response()?.errorBody()?.byteString().toString()
                    val json = jsonTemp.slice(6 until jsonTemp.length)
                    val errorCode = JSONObject(json).getString("code")

                    _postChangeTokenState.value = UiState.Failure(errorCode)
                }
            }
        }
    }

    companion object {
        const val KAKAO = "kakao"
    }
}
