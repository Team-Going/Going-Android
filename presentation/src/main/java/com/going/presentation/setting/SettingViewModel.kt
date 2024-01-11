package com.going.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.repository.SettingRepository
import com.going.domain.repository.TokenRepository
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    private val _userSignOutState = MutableStateFlow<Boolean?>(null)
    val userSignOutState: StateFlow<Boolean?> = _userSignOutState

    fun signOutKakao() {
        UserApiClient.instance.unlink { error ->
            _userSignOutState.value = null

            if (error == null) {
                signOutJwt()
            } else {
                _userSignOutState.value = false
            }
        }
    }

    private fun signOutJwt() {
        viewModelScope.launch {
            settingRepository.patchSignOut().onSuccess {
                tokenRepository.clearTokens()

                _userSignOutState.value = true
            }.onFailure {
                _userSignOutState.value = false
            }
        }
    }
}
