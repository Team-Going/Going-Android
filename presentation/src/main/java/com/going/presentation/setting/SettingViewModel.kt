package com.going.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.repository.SettingRepository
import com.going.domain.repository.TokenRepository
import com.going.ui.extension.EnumUiState
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
    private val _userSignOutState = MutableStateFlow(EnumUiState.LOADING)
    val userSignOutState: StateFlow<EnumUiState> = _userSignOutState

    private val _userWithDrawState = MutableStateFlow(EnumUiState.LOADING)
    val userWithDrawState: StateFlow<EnumUiState> = _userWithDrawState

    fun signOutKakao() {
        UserApiClient.instance.logout { error ->
            _userSignOutState.value = EnumUiState.LOADING

            if (error == null) {
                signOutJwt()
            } else {
                _userSignOutState.value = EnumUiState.FAILURE
            }
        }
    }

    private fun signOutJwt() {
        viewModelScope.launch {
            settingRepository.patchSignOut().onSuccess {
                tokenRepository.clearInfo()

                _userSignOutState.value = EnumUiState.SUCCESS
            }.onFailure {
                _userSignOutState.value = EnumUiState.FAILURE
            }
        }
    }

    fun startWithDrawKakao() {
        UserApiClient.instance.unlink { error ->
            _userWithDrawState.value = EnumUiState.LOADING

            if (error == null) {
                startWithDrawJwt()
            } else {
                _userWithDrawState.value = EnumUiState.FAILURE
            }
        }
    }

    private fun startWithDrawJwt() {
        viewModelScope.launch {
            settingRepository.deleteWithDraw().onSuccess {
                tokenRepository.clearInfo()

                _userWithDrawState.value = EnumUiState.SUCCESS
            }.onFailure {
                _userWithDrawState.value = EnumUiState.FAILURE
            }
        }
    }
}
