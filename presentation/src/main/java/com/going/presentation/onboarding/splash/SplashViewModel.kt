package com.going.presentation.onboarding.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.AuthState
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.TokenRepository
import com.going.presentation.onboarding.signin.SignInViewModel
import com.going.presentation.util.toErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    private val _userState = MutableStateFlow(AuthState.LOADING)
    val userState: StateFlow<AuthState> = _userState
    fun getHasAccessToken(): Boolean = tokenRepository.getAccessToken().isNotBlank()
    fun clear() = tokenRepository.clearTokens()

    fun getUserState() {
        viewModelScope.launch {
            authRepository.getSplash().onSuccess {
                _userState.value = AuthState.SUCCESS
            }.onFailure {
                val errorCode = toErrorCode(it)

                _userState.value = when (errorCode) {
                    TENDENCY -> AuthState.TENDENCY
                    else -> AuthState.FAILURE
                }
            }
        }
    }

    companion object {
        const val TENDENCY = "e4045"
    }
}
