package com.going.presentation.onboarding.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.TokenRepository
import com.going.domain.entity.AuthState
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
    private val _getUserState = MutableStateFlow(AuthState.LOADING)
    val getUserState: StateFlow<AuthState> = _getUserState
    fun getHasAccessToken(): Boolean = tokenRepository.getAccessToken().isNotBlank()
    fun clear() = tokenRepository.clearTokens()

    private fun getUserState() {
        viewModelScope.launch {
            authRepository.getSplash().onSuccess {
            }.onFailure {
            }
        }
    }
    // e4045 -> tendeny
}
