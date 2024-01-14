package com.going.presentation.onboarding.signup

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.AuthState
import com.going.domain.entity.NameState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOnboardingProfileSettingBinding
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.onboarding.splash.SplashActivity
import com.going.presentation.tendency.splash.TendencySplashActivity
import com.going.presentation.tendency.ttest.TendencyTestActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OnboardingProfileSettingActivity :
    BaseActivity<ActivityOnboardingProfileSettingBinding>(R.layout.activity_onboarding_profile_setting) {

    private var backPressedTime: Long = 0

    private val viewModel by viewModels<OnboardingProfileSettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        initOnLineInfoEditorActionListener()
        initSetOnFocusChangeListener()
        initSignUpBtnClickListener()
        observeIsNameAvailable()
        observeTextLength()
        observeIsSignUpState()
        initOnBackPressedListener()
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
            setColors(
                hasFocus,
                viewModel.nowNameLength.value ?: 0,
                binding.tvNameCounter,
            ) { background ->
                binding.etOnboardingProfileSettingName.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }

        binding.etOnboardingProfileSettingInfo.setOnFocusChangeListener { _, hasFocus ->
            setColors(
                hasFocus,
                viewModel.nowInfoLength.value ?: 0,
                binding.tvInfoCounter,
            ) { background ->
                binding.etOnboardingProfileSettingInfo.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun initSignUpBtnClickListener() {
        binding.btnOnboardingProfileSettingFinish.setOnSingleClickListener {
            viewModel.startSignUp()
        }
    }

    private fun observeIsNameAvailable() {
        viewModel.isNameAvailable.observe(this) { state ->
            setColors(
                false,
                viewModel.nowNameLength.value ?: 0,
                binding.tvNameCounter,
            ) { background ->
                binding.etOnboardingProfileSettingName.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun setColors(
        hasFocus: Boolean,
        length: Int,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when {
            viewModel.isNameAvailable.value != NameState.Blank && hasFocus -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            length == 0 -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            viewModel.isNameAvailable.value == NameState.Blank && counter == binding.tvNameCounter -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            else -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
        }

        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
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

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= SignInActivity.BACK_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    toast(getString(R.string.toast_back_pressed))
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}
