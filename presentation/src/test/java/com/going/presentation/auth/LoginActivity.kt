package com.going.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityLoginBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKakaoLoginBtnClickListener()
        observeInfo()
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSignIn.setOnSingleClickListener {
            viewModel.startKakaoLogIn(this)
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
        viewModel.postChangeTokenState.flowWithLifecycle(lifecycle).onEach { tokenState ->
            when (tokenState) {
                is UiState.Success -> {
                    // 성공 했을 때 로직
                }

                is UiState.Failure -> {
                    // 실패 했을 때 로직
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
}
