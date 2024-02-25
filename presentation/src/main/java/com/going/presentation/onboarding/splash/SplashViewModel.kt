package com.going.presentation.onboarding.splash

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.AuthState
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.TokenRepository
import com.going.presentation.util.toErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private fun getHasAccessToken(): Boolean = tokenRepository.getAccessToken().isNotBlank()

    fun initSplash(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            delay(DELAY_TIME)
            if (getHasAccessToken()) {
                getUserState()
            } else {
                _userState.value = AuthState.FAILURE
            }
        }
    }

    private fun getUserState() =
        viewModelScope.launch {
            authRepository.getSplash().onSuccess {
                _userState.value = AuthState.SUCCESS
            }.onFailure {
                val errorCode = toErrorCode(it)

                _userState.value = when (errorCode) {
                    TENDENCY -> AuthState.OTHER_PAGE
                    else -> AuthState.FAILURE
                }
            }
        }

    companion object {
        private const val DELAY_TIME = 2200L

        private const val TENDENCY = "e4045"
    }
}
