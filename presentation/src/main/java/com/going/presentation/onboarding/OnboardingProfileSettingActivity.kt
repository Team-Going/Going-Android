package com.going.presentation.onboarding

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
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
        initSetOnFocusChangeListener()
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

    private fun initSetOnFocusChangeListener() {
        binding.etOnboardingProfileSettingName.setOnFocusChangeListener { _, hasFocus ->
            judgeCounterColorWithFocus(
                viewModel.nowNameLength.value ?: 0,
                binding.tvNameCounter,
                hasFocus,
            ) {
                binding.etOnboardingProfileSettingName.background = ResourcesCompat.getDrawable(
                    this.resources,
                    it,
                    theme,
                )
            }
        }

        binding.etOnboardingProfileSettingInfo.setOnFocusChangeListener { _, hasFocus ->
            judgeCounterColorWithFocus(
                viewModel.nowInfoLength.value ?: 0,
                binding.tvInfoCounter,
                hasFocus,
            ) {
                binding.etOnboardingProfileSettingInfo.background = ResourcesCompat.getDrawable(
                    this.resources,
                    it,
                    theme,
                )
            }
        }
    }

    private fun judgeCounterColorWithFocus(
        nowLength: Int,
        counter: TextView,
        hasFocus: Boolean,
        setBackground: (Int) -> Unit,
    ) {
        if (hasFocus) {
            when (counter) {
                binding.tvNameCounter -> {
                    setBackground(
                        R.drawable.sel_rounded_corner_edit_text,
                    )
                }

                binding.tvInfoCounter -> {
                    setBackground(
                        R.drawable.sel_rounded_corner_edit_text,
                    )
                }
            }
            setCounterColor(counter, R.color.gray_700)
        } else {
            when (counter) {
                binding.tvNameCounter -> {
                    setColors(nowLength, counter) { background ->
                        setBackground(background)
                    }
                }

                binding.tvInfoCounter -> {
                    setColors(nowLength, counter) { background ->
                        setBackground(background)
                    }
                }
            }
        }

        if (counter == binding.tvNameCounter) {
            if (viewModel.isNameAvailable.value == NameState.Blank) {
                binding.tvNameCounter.setTextColor(getColor(R.color.red_500))
                setBackground(R.drawable.sel_rounded_corner_edit_text_error)
            }
        }
    }

    private fun setColors(length: Int, counter: TextView, setBackground: (Int) -> Unit) {
        val (color, background) = if (length == 0) {
            R.color.gray_200 to R.drawable.sel_rounded_corner_edit_text_empty
        } else {
            R.color.gray_700 to R.drawable.sel_rounded_corner_edit_text
        }

        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
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
                NameState.Blank -> {
                    binding.tvNameCounter.setTextColor(getColor(R.color.red_500))
                    binding.etOnboardingProfileSettingName.background =
                        getDrawable(R.drawable.sel_rounded_corner_edit_text_error)
                }

                else -> {
                    when (binding.etOnboardingProfileSettingName.hasFocus()) {
                        true -> {
                            binding.tvNameCounter.setTextColor(getColor(R.color.gray_700))
                            binding.etOnboardingProfileSettingName.background =
                                getDrawable(R.drawable.sel_rounded_corner_edit_text)
                        }

                        false -> if (viewModel.nowNameLength.value == 0) {
                            binding.tvNameCounter.setTextColor(
                                getColor(R.color.gray_200),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun moveSplash() {
        // 스플래시로 이동
    }
}
