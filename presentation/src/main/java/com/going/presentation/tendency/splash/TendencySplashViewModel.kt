package com.going.presentation.tendency.splash

import androidx.lifecycle.ViewModel

class TendencySplashViewModel : ViewModel() {
    var quarter: String = ""

    fun setQuarters(type: String) {
        quarter = type
    }
}