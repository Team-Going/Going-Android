package com.going.presentation.tendency.ttest

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestBinding
import com.going.presentation.tendency.result.TendencyResultActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.EnumUiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TendencyTestActivity :
    BaseActivity<ActivityTendencyTestBinding>(R.layout.activity_tendency_test) {

    private val viewModel by viewModels<TendencyTestViewModel>()

    private lateinit var fadeInList: List<ObjectAnimator>
    private lateinit var fadeOutList: List<ObjectAnimator>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        initFadeAnimation()
        initFadeListener()
        initNextBtnClickListener()
        observeButtonSelected()
        observeIsSubmitTendencyState()
        initOnBackPressedListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun initFadeAnimation() {
        fadeOutList = listOf<ObjectAnimator>(
            ObjectAnimator.ofFloat(binding.tvFirstAnswer, ALPHA, 1f, 0f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvSecondAnswer, ALPHA, 1f, 0f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvThirdAnswer, ALPHA, 1f, 0f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvFourthAnswer, ALPHA, 1f, 0f).apply {
                duration = DURATION
            },
        )

        fadeInList = listOf<ObjectAnimator>(
            ObjectAnimator.ofFloat(binding.tvFirstAnswer, ALPHA, 0f, 1f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvSecondAnswer, ALPHA, 0f, 1f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvThirdAnswer, ALPHA, 0f, 1f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvFourthAnswer, ALPHA, 0f, 1f).apply {
                duration = DURATION
            },
        )
    }

    private fun initFadeListener() {
        fadeOutList[0].addListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    viewModel.clearAllChecked()
                    setProgressAnimate(binding.pbTendencyTest, viewModel.step.value + 1)

                    for (i in 1 until fadeOutList.size) {
                        fadeOutList[i].start()
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    viewModel.plusStepValue()

                    fadeInList.map {
                        it.start()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                    //
                }

                override fun onAnimationRepeat(animation: Animator) {
                    //
                }
            },
        )
    }

    private fun setProgressAnimate(pb: ProgressBar, progressTo: Int) =
        ObjectAnimator.ofInt(pb, PROGRESS, pb.progress, progressTo * 100).apply {
            duration = DURATION
            setAutoCancel(true)
            interpolator = DecelerateInterpolator()
            start()
        }

    private fun initNextBtnClickListener() {
        binding.btnTendencyNext.setOnSingleClickListener {
            when (viewModel.step.value) {
                9 -> viewModel.submitTendencyTest()
                else -> fadeOutList[0].start()
            }
        }
    }

    private fun observeButtonSelected() {
        viewModel.isFirstChecked.flowWithLifecycle(lifecycle).onEach {
            binding.tvFirstAnswer.setTextAppearance(setFont(it))
        }.launchIn(lifecycleScope)

        viewModel.isSecondChecked.flowWithLifecycle(lifecycle).onEach {
            binding.tvSecondAnswer.setTextAppearance(setFont(it))
        }.launchIn(lifecycleScope)

        viewModel.isThirdChecked.flowWithLifecycle(lifecycle).onEach {
            binding.tvThirdAnswer.setTextAppearance(setFont(it))
        }.launchIn(lifecycleScope)

        viewModel.isFourthChecked.flowWithLifecycle(lifecycle).onEach {
            binding.tvFourthAnswer.setTextAppearance(setFont(it))
        }.launchIn(lifecycleScope)
    }

    private fun setFont(checked: Boolean) = if (checked) {
        R.style.TextAppearance_Doorip_Body3_Bold
    } else {
        R.style.TextAppearance_Doorip_Body3_Medi
    }

    private fun observeIsSubmitTendencyState() {
        viewModel.isSubmitTendencyState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                EnumUiState.LOADING -> {}
                EnumUiState.SUCCESS -> navigateToTendencyResultScreen()
                EnumUiState.FAILURE -> toast(getString(R.string.server_error))
                EnumUiState.EMPTY -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToTendencyResultScreen() {
        Intent(this, TendencyResultActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        const val DURATION = 500L

        const val PROGRESS = "progress"
        const val ALPHA = "alpha"
    }
}
