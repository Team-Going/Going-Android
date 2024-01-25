package com.going.presentation.onboarding.splash

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.AuthState
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivitySplashBinding
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.navigateToScreen
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setStatusBarColorFromResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor()
        checkConnectedNetwork()
        observeUserState()
    }

    private fun setStatusBarColor() {
        setStatusBarColorFromResource(R.color.red_500)
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
            if (viewModel.getHasAccessToken()) {
                viewModel.getUserState()
            } else {
                navigateToScreen<SignInActivity>()
            }
        }, 2200)
    }

    private fun observeUserState() {
        viewModel.userState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                AuthState.SUCCESS -> navigateToScreen<DashBoardActivity>()
                AuthState.FAILURE -> navigateToScreen<SignInActivity>()
                AuthState.TENDENCY -> navigateToScreen<TendencySplashActivity>()
                else -> return@onEach
            }
        }.launchIn(lifecycleScope)
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
}
