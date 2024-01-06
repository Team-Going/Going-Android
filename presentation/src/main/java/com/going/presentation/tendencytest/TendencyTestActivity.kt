package com.going.presentation.tendencytest

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TendencyTestActivity :
    BaseActivity<ActivityTendencyTestBinding>(R.layout.activity_tendency_test) {

    private lateinit var fadeInList: List<ObjectAnimator>
    private lateinit var fadeOutList: List<ObjectAnimator>

    private val viewModel by viewModels<TendencyTestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        initFadeAnimation()
        initFadeListener()
        initNextBtnClickListener()
        observeButtonSelected()
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
                    fadeOutList.map {
                        it.start()
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
                9 -> moveTendencyTestResultActivity()
                else -> fadeOutList[0].start()
            }
        }
    }

    private fun moveTendencyTestResultActivity() {
        // 페이지 이동 기능 추가 예정
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

    companion object {
        const val DURATION = 500L

        const val PROGRESS = "progress"
        const val ALPHA = "alpha"
    }
}
