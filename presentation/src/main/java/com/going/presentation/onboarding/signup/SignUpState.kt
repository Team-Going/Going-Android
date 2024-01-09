package com.going.presentation.onboarding.signup

sealed interface SignUpState {
    object LOADING : SignUpState
    object SUCCESS : SignUpState
    object FAIL : SignUpState
    object LOG_IN : SignUpState
}
