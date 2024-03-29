package com.going.presentation.onboarding.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.AuthState
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySignUpBinding
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.initOnBackPressedListener
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpActivity :
    BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        setEtNameArguments()
        setEtInfoArguments()
        initSignUpBtnClickListener()
        observeNameTextChanged()
        observeInfoTextChanged()
        observeIsSignUpState()
        initOnBackPressedListener(binding.root)
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun setEtNameArguments() {
        with(binding.etSignUpName) {
            setMaxLen(viewModel.getMaxNameLen())
            overWarning = getString(R.string.name_over_error)
            blankWarning = getString(R.string.name_blank_error)
        }
    }

    private fun setEtInfoArguments() {
        with(binding.etSignUpInfo) {
            setMaxLen(viewModel.getMaxInfoLen())
            overWarning = getString(R.string.info_over_error)
        }
    }

    private fun initSignUpBtnClickListener() {
        binding.btnSignUpFinish.setOnSingleClickListener {
            viewModel.startSignUp()
        }
    }

    private fun observeNameTextChanged() {
        binding.etSignUpName.editText.doAfterTextChanged { text ->
            viewModel.setNameState(text.toString(), binding.etSignUpName.state)
        }
    }

    private fun observeInfoTextChanged() {
        binding.etSignUpInfo.editText.doAfterTextChanged { text ->
            viewModel.setInfoState(text.toString(), binding.etSignUpInfo.state)
        }
    }

    private fun observeIsSignUpState() {
        viewModel.isAuthState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                AuthState.LOADING -> return@onEach
                AuthState.SUCCESS -> {
                    TendencySplashActivity.createIntent(
                        this,
                        TendencySplashActivity.TENDENCY
                    ).apply {
                        startActivity(this)
                        finish()
                    }
                }

                AuthState.FAILURE -> toast(getString(R.string.server_error))
                AuthState.OTHER_PAGE -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }
}
