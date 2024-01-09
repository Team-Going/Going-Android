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
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
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
                is UiState.Success -> {
                    // 쉐어드 프리퍼런스 값 저장 로직
                    navigateToMainScreen()
                }

                is UiState.Failure -> {
                    // 실패 했을 때 로직
                    when (state.msg) {
                        CODE_NOT_SIGNED_IN -> navigateToOnboardingScreen()
                        CODE_ALREADY_SIGNED_UP -> navigateToMainScreen()
                        // else로 에러 컨트롤 필요! else는 client error / server error만 존재
                    }
                }

                is UiState.Empty -> {
                    // 여튼 로직
                }

                is UiState.Loading -> {
                    // 로딩 중 로직
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToOnboardingScreen() {
        Intent(this, OnboardingProfileSettingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToMainScreen() {
        // 추후 대시보드 연결시 연결 예정
        Intent(this, OnboardingProfileSettingActivity::class.java).apply {
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
