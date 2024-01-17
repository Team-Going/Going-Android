package com.going.presentation.onboarding.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.AuthState
import com.going.domain.entity.NameState
import com.going.domain.entity.request.SignUpRequestModel
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.TokenRepository
import com.going.ui.extension.getGraphemeLength
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingProfileSettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    val name = MutableStateFlow("")
    val nowNameLength = MutableLiveData(0)
    val info = MutableStateFlow("")
    val nowInfoLength = MutableLiveData(0)

    val isNameAvailable = MutableLiveData(NameState.Empty)
    val isInfoAvailable = MutableLiveData(NameState.Empty)
    val isProfileAvailable = MutableLiveData(false)

    private val _isSignUpState = MutableStateFlow(AuthState.LOADING)
    val isSignUpState: StateFlow<AuthState> = _isSignUpState

    fun checkProfileAvailable() {
        nowNameLength.value = name.value.getGraphemeLength()
        nowInfoLength.value = info.value.getGraphemeLength()

        isNameAvailable.value = when {
            nowNameLength.value == 0 -> NameState.Empty
            name.value.isBlank() -> NameState.Blank
            (nowNameLength.value ?: 0) > 3 -> NameState.OVER
            else -> NameState.Success
        }

        isInfoAvailable.value = when {
            nowInfoLength.value == 0 -> NameState.Empty
            (nowInfoLength.value ?: 0) > MAX_INFO_LEN -> NameState.OVER
            else -> NameState.Success
        }

        isProfileAvailable.value =
            (isNameAvailable.value == NameState.Success) && (isInfoAvailable.value == NameState.Success)
    }

    fun startSignUp() {
        _isSignUpState.value = AuthState.LOADING

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    val kakaoAccessToken =
                        TokenManagerProvider.instance.manager.getToken()?.accessToken
                    signUpWithServer(kakaoAccessToken.toString())
                } else {
                    _isSignUpState.value = AuthState.SIGNIN
                }
            }
        } else {
            _isSignUpState.value = AuthState.SIGNIN
        }
    }

    private fun signUpWithServer(kakaoAccessToken: String) {
        viewModelScope.launch {
            authRepository.postSignUp(
                kakaoAccessToken,
                SignUpRequestModel(name.value, info.value, KAKAO),
            ).onSuccess {
                tokenRepository.setTokens(it.accessToken, it.refreshToken)
                tokenRepository.setUserId(it.userId)
                _isSignUpState.value = AuthState.SUCCESS
            }.onFailure {
                _isSignUpState.value = AuthState.FAILURE
            }
        }
    }

    companion object {
        const val KAKAO = "kakao"
        const val MAX_NAME_LEN = 3
        const val MAX_INFO_LEN = 20
    }
}
