package com.going.presentation.onboarding

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.NameState
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
        initSetOnFucusChangeListener()
        observeIsProfileAvailable()
        observeTextLength()
        observeIsNameAvailable()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun initOnLineInfoEditorActionListener() {
        binding.etOnboardingProfileSettingInfo.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) view.clearFocus()
            false
        }
    }

    private fun initSetOnFucusChangeListener() {
        binding.etOnboardingProfileSettingName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.tvNameCounter.setTextColor(getColor(R.color.gray_700))
            } else {
                binding.tvNameCounter.setTextColor(getColor(R.color.gray_200))
            }
            if (viewModel.isNameAvailable.value == NameState.Blank) {
                binding.tvNameCounter.setTextColor(
                    getColor(R.color.red_400),
                )
            }
        }

        binding.etOnboardingProfileSettingInfo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.tvNameCounter.setTextColor(getColor(R.color.gray_700))
            } else {
                binding.tvNameCounter.setTextColor(getColor(R.color.gray_200))
            }
            if (viewModel.isNameAvailable.value == NameState.Blank) {
                binding.tvNameCounter.setTextColor(
                    getColor(R.color.red_400),
                )
            }
        }
    }

    private fun observeIsProfileAvailable() {
        viewModel.isMoveScreenAvailable.flowWithLifecycle(lifecycle).onEach { isEnd ->
            if (isEnd) moveSplash()
        }.launchIn(lifecycleScope)
    }

    // 커스텀 글자수 제한 함수
    private fun observeTextLength() {
        viewModel.nowNameLength.observe(this) { length ->
            val maxNameLength = viewModel.getMaxNameLen()

            if (length > maxNameLength) {
                binding.etOnboardingProfileSettingName.apply {
                    setText(text?.subSequence(0, maxNameLength))
                    setSelection(maxNameLength)
                }
            }
        }

        viewModel.nowInfoLength.observe(this) { length ->
            val maxInfoLength = viewModel.getMaxInfoLen()

            if (length > maxInfoLength) {
                binding.etOnboardingProfileSettingInfo.apply {
                    setText(text?.subSequence(0, maxInfoLength))
                    setSelection(maxInfoLength)
                }
            }
        }
    }

    private fun observeIsNameAvailable() {
        viewModel.isNameAvailable.observe(this) { state ->
            when (state) {
                NameState.Blank -> binding.tvNameCounter.setTextColor(getColor(R.color.red_400))
                else -> binding.tvNameCounter.setTextColor(getColor(R.color.gray_700))
            }
        }
    }

    private fun moveSplash() {
        // 스플래시로 이동
    }
}
