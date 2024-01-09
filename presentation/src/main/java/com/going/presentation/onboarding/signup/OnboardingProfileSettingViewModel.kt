package com.going.presentation.onboarding.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.NameState
import com.going.domain.entity.request.RequestSignUpModel
import com.going.domain.repository.AuthRepository
import com.going.presentation.util.toErrorCode
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.BreakIterator
import javax.inject.Inject

@HiltViewModel
class OnboardingProfileSettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val name = MutableStateFlow("")
    val nowNameLength = MutableLiveData(0)
    val info = MutableStateFlow("")
    val nowInfoLength = MutableLiveData(0)

    val isNameAvailable = MutableLiveData(NameState.Empty)
    val isProfileAvailable = MutableLiveData(false)

    private val _isSignUpState = MutableStateFlow<SignUpState>(SignUpState.LOADING)
    val isSignUpState: StateFlow<SignUpState> = _isSignUpState

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
        _isSignUpState.value = SignUpState.LOADING

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    val kakaoAccessToken =
                        TokenManagerProvider.instance.manager.getToken()?.accessToken
                    signUpWithServer(kakaoAccessToken.toString())
                } else {
                    _isSignUpState.value = SignUpState.LOG_IN
                }
            }
        } else {
            _isSignUpState.value = SignUpState.LOG_IN
        }
    }

    private fun signUpWithServer(kakaoAccessToken: String) {
        viewModelScope.launch {
            authRepository.postSignUp(
                kakaoAccessToken,
                RequestSignUpModel(name.value, info.value, KAKAO),
            ).onSuccess {
                _isSignUpState.value = SignUpState.SUCCESS
            }.onFailure {
                _isSignUpState.value = SignUpState.FAIL
            }
        }
    }

    companion object {
        val BREAK_ITERATOR: BreakIterator = BreakIterator.getCharacterInstance()

        const val KAKAO = "kakao"
        const val MAX_NAME_LEN = 3
        const val MAX_INFO_LEN = 20
        const val SIGN_UP = "e4041"
        const val TENDENCY = "e4045"
    }
}
