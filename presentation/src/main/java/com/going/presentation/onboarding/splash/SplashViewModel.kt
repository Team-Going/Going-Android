package com.going.presentation.onboarding.splash

import androidx.lifecycle.ViewModel
import com.going.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    fun getHasAccessToken(): Boolean = tokenRepository.getAccessToken().isNotBlank()

    fun clearTokens() = tokenRepository.clearTokens()
}
