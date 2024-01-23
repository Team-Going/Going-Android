package com.going.presentation.onboarding.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.AuthState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOnboardingProfileSettingBinding
import com.going.presentation.onboarding.splash.SplashActivity
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.util.initOnBackPressedListener
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OnboardingProfileSettingActivity :
    BaseActivity<ActivityOnboardingProfileSettingBinding>(R.layout.activity_onboarding_profile_setting) {

    private val viewModel by viewModels<OnboardingProfileSettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        setEtNameArguments()
        setEtInfoArguments()
        initSignUpBtnClickListener()
        observeNameTextChanged()
        observeInfoTextChanged()
        observeIsSignUpState()
        initOnBackPressedListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun setEtNameArguments() {
        with(binding.etOnboardingProfileSettingName) {
            setMaxLen(viewModel.getMaxNameLen())
            overWarning = getString(R.string.name_over_error)
            blankWarning = getString(R.string.name_blank_error)
        }
    }

    private fun setEtInfoArguments() {
        with(binding.etOnboardingProfileSettingInfo) {
            setMaxLen(viewModel.getMaxInfoLen())
            overWarning = getString(R.string.info_over_error)
        }
    }

    private fun initSignUpBtnClickListener() {
        binding.btnOnboardingProfileSettingFinish.setOnSingleClickListener {
            viewModel.startSignUp()
        }
    }

    private fun observeNameTextChanged() {
        binding.etOnboardingProfileSettingName.editText.doAfterTextChanged {
            viewModel.setNameState(it.toString(), binding.etOnboardingProfileSettingName.state)
        }
    }

    private fun observeInfoTextChanged() {
        binding.etOnboardingProfileSettingInfo.editText.doAfterTextChanged {
            viewModel.setInfoState(it.toString(), binding.etOnboardingProfileSettingInfo.state)
        }
    }

    private fun observeIsSignUpState() {
        viewModel.isSignUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                AuthState.LOADING -> return@onEach
                AuthState.SUCCESS -> navigateToTendencySplashScreen()
                AuthState.FAILURE -> toast(getString(R.string.server_error))
                AuthState.SIGNUP -> return@onEach
                AuthState.SIGNIN -> navigateToSplashScreen()
                AuthState.TENDENCY -> return@onEach
                AuthState.EMPTY -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToSplashScreen() {
        Intent(this, SplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun navigateToTendencySplashScreen() {
        Intent(this, TendencySplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }
}
