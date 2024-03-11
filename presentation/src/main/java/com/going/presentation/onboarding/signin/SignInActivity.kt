package com.going.presentation.onboarding.signin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.SignInState
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivitySignInBinding
import com.going.presentation.onboarding.signup.SignUpActivity
import com.going.presentation.setting.SettingActivity.Companion.TERMS_URL
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.initOnBackPressedListener
import com.going.presentation.util.navigateToScreenClear
import com.going.presentation.util.openWebView
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clearToken()
        initKakaoLoginBtnClickListener()
        initTermsBtnClickListener()
        observeInfo()
        initOnBackPressedListener(binding.root)
    }

    private fun clearToken() {
        viewModel.clearToken()
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSignIn.setOnSingleClickListener {
            viewModel.startKakaoLogIn(this)
        }
    }

    private fun initTermsBtnClickListener() {
        binding.btnTerms.setOnSingleClickListener {
            openWebView(TERMS_URL)
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
                SignInState.LOADING -> return@onEach
                SignInState.SUCCESS -> navigateToScreenClear<DashBoardActivity>()
                SignInState.FAILURE -> toast(getString(R.string.server_error))
                SignInState.SIGNUP -> navigateToScreenClear<SignUpActivity>()
                SignInState.TENDENCY -> {
                    TendencySplashActivity.createIntent(this, TendencySplashActivity.TENDENCY)
                }
            }
        }.launchIn(lifecycleScope)
    }
}
