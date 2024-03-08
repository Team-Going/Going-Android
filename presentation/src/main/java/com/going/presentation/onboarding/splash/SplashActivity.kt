package com.going.presentation.onboarding.splash

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivitySplashBinding
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.util.navigateToScreenClear
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setNavigationBarColorFromResource
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
        setNavigationBarColor()
        checkConnectedNetwork()
        observeUserState()
    }

    private fun setStatusBarColor() = setStatusBarColorFromResource(R.color.red_500)

    private fun setNavigationBarColor() = setNavigationBarColorFromResource(R.color.red_500)

    private fun checkConnectedNetwork() {
        if (NetworkManager.checkNetworkState(this)) {
            viewModel.initSplash(this)
        } else {
            showNetworkErrorAlertDialog()
        }
    }

    private fun observeUserState() {
        viewModel.userState.flowWithLifecycle(lifecycle).onEach { state ->
            if (state) navigateToScreenClear<DashBoardActivity>()
            else navigateToScreenClear<SignInActivity>()
        }.launchIn(lifecycleScope)
    }

    private fun showNetworkErrorAlertDialog() = AlertDialog.Builder(this).setTitle(R.string.notice)
        .setMessage(R.string.internet_connect_error).setCancelable(false).setPositiveButton(
            R.string.okay,
        ) { _, _ ->
            finishAffinity()
        }.create().show()
}
