package com.going.presentation.onboarding.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.SignUpState
import com.going.domain.entity.request.SignUpRequestModel
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.TokenRepository
import com.going.presentation.designsystem.edittext.EditTextState
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    private val name = MutableStateFlow("")
    private val info = MutableStateFlow("")

    val isNameAvailable = MutableStateFlow(false)
    val isInfoAvailable = MutableStateFlow(false)

    private val _isSignUpState = MutableStateFlow(SignUpState.LOADING)
    val isSignUpState: StateFlow<SignUpState> = _isSignUpState

    fun startSignUp() {
        _isSignUpState.value = SignUpState.LOADING

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    val kakaoAccessToken =
                        TokenManagerProvider.instance.manager.getToken()?.accessToken.toString()
                    signUpWithServer(kakaoAccessToken)
                    return@accessTokenInfo
                }
            }
        }

        _isSignUpState.value = SignUpState.SPLASH
    }

    private fun signUpWithServer(kakaoAccessToken: String) {
        viewModelScope.launch {
            authRepository.postSignUp(
                kakaoAccessToken,
                SignUpRequestModel(name.value, info.value, KAKAO),
            ).onSuccess {
                tokenRepository.setTokens(it.accessToken, it.refreshToken)
                tokenRepository.setUserId(it.userId)
                _isSignUpState.value = SignUpState.SUCCESS
            }.onFailure {
                _isSignUpState.value = SignUpState.FAILURE
            }
        }
    }

    fun setNameState(newName: String, state: EditTextState) {
        name.value = newName
        isNameAvailable.value = state == EditTextState.SUCCESS
    }

    fun setInfoState(newInfo: String, state: EditTextState) {
        info.value = newInfo
        isInfoAvailable.value = state == EditTextState.SUCCESS
    }

    fun getMaxNameLen() = MAX_NAME_LEN

    fun getMaxInfoLen() = MAX_INFO_LEN

    companion object {
        const val KAKAO = "kakao"
        const val MAX_NAME_LEN = 3
        const val MAX_INFO_LEN = 20
    }
}
