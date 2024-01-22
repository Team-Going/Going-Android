package com.going.presentation.onboarding.signup

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.AuthState
import com.going.domain.entity.NameState
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
        initOnLineInfoEditorActionListener()
        initSignUpBtnClickListener()
        // observeIsNameAvailable()
        observeIsInfoAvailable()
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

    private fun initSignUpBtnClickListener() {
        binding.btnOnboardingProfileSettingFinish.setOnSingleClickListener {
            viewModel.startSignUp()
        }
    }

//    private fun observeIsNameAvailable() {
//        viewModel.isNameAvailable.observe(this) { state ->
//            setColors(
//                binding.tvNameCounter,
//                state,
//            ) { background ->
//                binding.etOnboardingProfileSettingName.background = ResourcesCompat.getDrawable(
//                    this.resources,
//                    background,
//                    theme,
//                )
//            }
//        }
//    }

    private fun observeIsInfoAvailable() {
        viewModel.isInfoAvailable.observe(this) { state ->
            setColors(
                binding.tvInfoCounter,
                state,
            ) { background ->
                binding.etOnboardingProfileSettingInfo.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun setColors(
        counter: TextView,
        state: NameState,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when (state) {
            NameState.Empty -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            NameState.Success -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            NameState.Blank -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            NameState.OVER -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
        }

        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
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
