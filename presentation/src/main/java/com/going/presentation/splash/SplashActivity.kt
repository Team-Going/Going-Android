package com.going.presentation.splash

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import com.going.presentation.R
import com.going.presentation.auth.SignInActivity
import com.going.presentation.databinding.ActivitySplashBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.toast

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
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

    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToSignInScreen()
            if (false) { // 자동 로그인 판정으로 변경 예정
                navigateToMainScreen()
            } else {
                navigateToSignInScreen()
            }
        }, 3000)
    }

    private fun navigateToMainScreen() {
        // Main이 나오면 구현 예정
        finish()
    }

    private fun navigateToSignInScreen() {
        Intent(this, SignInActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode != RESULT_OK) {
                toast(getString(R.string.splash_update_error))
                finishAffinity()
            }
        }
}
