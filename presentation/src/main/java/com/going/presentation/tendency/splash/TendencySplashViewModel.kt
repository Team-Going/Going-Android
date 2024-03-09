package com.going.presentation.tendency.splash

import androidx.lifecycle.ViewModel

class TendencySplashViewModel: ViewModel() {
    lateinit var quarter: String

    fun setQuarter(type: String) {
        quarter = type
    }
}