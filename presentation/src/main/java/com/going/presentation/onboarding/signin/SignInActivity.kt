package com.going.presentation.onboarding.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySigninBinding
import com.going.presentation.onboarding.signup.OnboardingProfileSettingActivity
import com.going.presentation.tendencytest.TendencyTestActivity
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
                SignInState.SUCCESS -> navigateToMainScreen()
                SignInState.SIGN_UP -> navigateToOnboardingScreen()
                SignInState.TENDENCY -> navigateToTendencyScreen()
                SignInState.FAIL -> toast(getString(R.string.server_error))
                SignInState.LOADING -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMainScreen() {
        // 추후 대시보드 연결시 연결 예정
        Intent(this, OnboardingProfileSettingActivity::class.java).apply {
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
        Intent(this, TendencyTestActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    companion object {
        const val TERMS_URL = "http://www.naver.com"
        const val CODE_NOT_SIGNED_IN = "e4041"
        const val CODE_ALREADY_SIGNED_UP = "e4090"
    }
}
