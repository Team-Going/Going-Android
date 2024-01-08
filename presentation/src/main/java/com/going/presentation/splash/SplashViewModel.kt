package com.going.presentation.splash

import androidx.lifecycle.ViewModel
import com.going.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    fun getHasAccessToken(): Boolean = authRepository.getAccessToken() != ""
}
