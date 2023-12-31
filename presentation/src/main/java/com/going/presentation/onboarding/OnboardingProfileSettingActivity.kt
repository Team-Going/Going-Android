package com.going.presentation.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOnboardingProfileSettingBinding
import com.going.ui.base.BaseActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OnboardingProfileSettingActivity :
    BaseActivity<ActivityOnboardingProfileSettingBinding>(R.layout.activity_onboarding_profile_setting) {
    private val viewModel by viewModels<OnboardingProfileSettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeIsProfileAvailable()
    }

    // 이거 필요없네 ㅋㅋㅋㅋㅋㅋㅋ
    private fun observeIsProfileAvailable() {
        viewModel.isProfileAvailable.flowWithLifecycle(lifecycle).onEach { isEnd ->
            if (isEnd) moveSplash()
        }.launchIn(lifecycleScope)
    }

    private fun moveSplash() {
        // 스플래시로 이동
    }
}
