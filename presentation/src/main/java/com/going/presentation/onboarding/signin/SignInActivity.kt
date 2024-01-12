package com.going.presentation.onboarding.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.AuthState
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivitySigninBinding
import com.going.presentation.onboarding.signup.OnboardingProfileSettingActivity
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKakaoLoginBtnClickListener()
        initTermsBtnClickListener()
        observeInfo()
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSignIn.setOnSingleClickListener {
            viewModel.startKakaoLogIn(this)
        }
    }

    private fun initTermsBtnClickListener() {
        binding.btnTerms.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_URL)).apply {
                startActivity(this)
            }
        }
    }

    private fun observeInfo() {
        observeIsAppLoginAvailable()
        observePostChangeTokenState()
    }

    private fun observeIsAppLoginAvailable() {
        viewModel.isAppLoginAvailable.flowWithLifecycle(lifecycle).onEach { canLogin ->
            if (!canLogin) viewModel.startKakaoLogIn(this)
        }.launchIn(lifecycleScope)
    }

    private fun observePostChangeTokenState() {
        viewModel.postChangeTokenState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                AuthState.LOADING -> return@onEach
                AuthState.SUCCESS -> navigateToDashBoardScreen()
                AuthState.FAILURE -> toast(getString(R.string.server_error))
                AuthState.SIGNUP -> navigateToOnboardingScreen()
                AuthState.SIGNIN -> return@onEach
                AuthState.TENDENCY -> navigateToTendencyScreen()
                AuthState.EMPTY -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToDashBoardScreen() {
        Intent(this, DashBoardActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToOnboardingScreen() {
        Intent(this, OnboardingProfileSettingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToTendencyScreen() {
        Intent(this, TendencySplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    companion object {
        const val TERMS_URL = "http://www.naver.com"
    }
}
