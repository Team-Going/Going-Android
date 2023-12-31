package com.going.presentation.onboarding

import android.os.Bundle
import android.view.inputmethod.EditorInfo
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

        initBindingViewModel()
        initOnLineInfoEditorActionListener()
        observeIsProfileAvailable()
        observeTextLength()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun initOnLineInfoEditorActionListener() {
        binding.etvOnboardingProfileSettingOnLineInfo.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) view.clearFocus()
            false
        }
    }

    private fun observeIsProfileAvailable() {
        viewModel.isMoveScreenAvailable.flowWithLifecycle(lifecycle).onEach { isEnd ->
            if (isEnd) moveSplash()
        }.launchIn(lifecycleScope)
    }

    // 커스텀 글자수 제한 함수 -> 리펙토링 반드시 필요!!! 그저 기능구현만 해봄
    private fun observeTextLength() {
        viewModel.nameLen.observe(this) { length ->
            if (length > 5) {
                binding.etvOnboardingProfileSettingName.setText(
                    binding.etvOnboardingProfileSettingName.text?.subSequence(
                        0,
                        5,
                    ),
                )
                binding.etvOnboardingProfileSettingName.setSelection(5)
            }
        }

        viewModel.infoLen.observe(this) { length ->
            if (length > 20) {
                binding.etvOnboardingProfileSettingOnLineInfo.setText(
                    binding.etvOnboardingProfileSettingOnLineInfo.text?.subSequence(
                        0,
                        20,
                    ),
                )
                binding.etvOnboardingProfileSettingOnLineInfo.setSelection(20)
            }
        }
    }

    private fun moveSplash() {
        // 스플래시로 이동
    }
}
