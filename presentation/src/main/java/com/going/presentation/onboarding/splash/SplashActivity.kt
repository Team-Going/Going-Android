package com.going.presentation.onboarding.splash

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySplashBinding
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.onboarding.signup.OnboardingProfileSettingActivity
import com.going.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkConnectedNetwork()
    }

    private fun checkConnectedNetwork() {
        if (NetworkManager.checkNetworkState(this)) {
            initSplash()
        } else {
            showNetworkErrorAlertDialog()
        }
    }

    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.clear()

            if (viewModel.getHasAccessToken()) {
                navigateToMainScreen()
            } else {
                // api 호출로 변경 예정
                navigateToSignInScreen()
            }
        }, 3000)
    }

    private fun showNetworkErrorAlertDialog() =
        AlertDialog.Builder(this)
            .setTitle(R.string.notice)
            .setMessage(R.string.internet_connect_error)
            .setCancelable(false)
            .setPositiveButton(
                R.string.okay,
            ) { _, _ ->
                finishAffinity()
            }
            .create()
            .show()

    private fun navigateToMainScreen() {
        // Main이 나오면 구현 예정
        Intent(this, OnboardingProfileSettingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToSignInScreen() {
        Intent(this, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }
}
