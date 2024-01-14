package com.going.presentation.tendency.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencySplashBinding
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.tendency.ttest.TendencyTestActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class TendencySplashActivity :
    BaseActivity<ActivityTendencySplashBinding>(R.layout.activity_tendency_splash) {

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartBtnClickListener()
        initOnBackPressedListener()
    }

    private fun initStartBtnClickListener() {
        binding.btnTendencySplashStart.setOnSingleClickListener {
            navigateToTendencyTestScreen()
        }
    }

    private fun navigateToTendencyTestScreen() {
        Intent(this, TendencyTestActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= SignInActivity.BACK_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    toast(getString(R.string.toast_back_pressed))
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}
