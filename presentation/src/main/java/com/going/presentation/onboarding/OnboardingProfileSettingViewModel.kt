package com.going.presentation.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import com.going.domain.entity.response.AuthTokenModel
import com.going.ui.extension.UiState
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.BreakIterator

class OnboardingProfileSettingViewModel : ViewModel() {
    val name = MutableLiveData(String())
    val nowNameLength = MutableLiveData(0)
    val info = MutableLiveData(String())
    val nowInfoLength = MutableLiveData(0)

    val isNameAvailable = MutableLiveData(NameState.Empty)
    val isProfileAvailable = MutableLiveData(false)

    private val _isTokenState = MutableStateFlow<UiState<AuthTokenModel>>(UiState.Empty)
    val isTokenState: StateFlow<UiState<AuthTokenModel?>> = _isTokenState

    fun getMaxNameLen() = MAX_NAME_LEN
    fun getMaxInfoLen() = MAX_INFO_LEN

    fun checkProfileAvailable() {
        nowNameLength.value = getGraphemeLength(name.value)
        nowInfoLength.value = getGraphemeLength(info.value)

        isNameAvailable.value = when {
            nowNameLength.value == 0 -> NameState.Empty
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }

        val isInfoAvailable = getGraphemeLength(info.value) in 1..MAX_INFO_LEN

        isProfileAvailable.value =
            (isNameAvailable.value == NameState.Success) && isInfoAvailable
    }

    // 이모지 포함 글자 수 세는 함수
    private fun getGraphemeLength(value: String?): Int {
        BREAK_ITERATOR.setText(value)

        var count = 0
        while (BREAK_ITERATOR.next() != BreakIterator.DONE) {
            count++
        }

        return count
    }

    fun startSignUp() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        _isTokenState.value = UiState.Failure("kakao error")
                    } else {
                        _isTokenState.value = UiState.Failure("kakao error")
                    }
                } else {
                    val kakaoAccessToken =
                        TokenManagerProvider.instance.manager.getToken()?.accessToken
                    signUpWithServer(kakaoAccessToken.toString())
                }
            }
        } else {
            _isTokenState.value = UiState.Failure("kakao error")
        }
    }

    private fun signUpWithServer(kakaoAccessToken: String) {
        // 서버와 통신 예정
    }

    companion object {
        val BREAK_ITERATOR: BreakIterator = BreakIterator.getCharacterInstance()

        const val MAX_NAME_LEN = 3
        const val MAX_INFO_LEN = 20
    }
}
