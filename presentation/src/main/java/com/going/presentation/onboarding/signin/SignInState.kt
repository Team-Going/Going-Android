package com.going.presentation.onboarding.signin

sealed interface SignInState {
    object LOADING : SignInState
    object SUCCESS : SignInState
    object FAIL : SignInState
    object SIGN_UP : SignInState
    object TENDENCY : SignInState
}
