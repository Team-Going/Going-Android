package com.going.presentation.tendencytest

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencyTestActivity :
    BaseActivity<ActivityTendencyTestBinding>(R.layout.activity_tendency_test) {

    private lateinit var fadeInQuestion: ObjectAnimator
    private lateinit var fadeOutQuestion: ObjectAnimator
    private lateinit var fadeInList: List<ObjectAnimator>
    private lateinit var fadeOutList: List<ObjectAnimator>

    private val viewModel by viewModels<TendencyTestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        initFadeAnimation()
        initFadeListener()
        initNextBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun initFadeAnimation() {
        fadeInQuestion =
            ObjectAnimator.ofFloat(binding.tvTendencyTestQuestion, "alpha", 0f, 1f).apply {
                duration = DURATION
            }

        fadeOutQuestion =
            ObjectAnimator.ofFloat(binding.tvTendencyTestQuestion, "alpha", 1f, 0f).apply {
                duration = DURATION
            }

        fadeOutList = listOf<ObjectAnimator>(
            ObjectAnimator.ofFloat(binding.tvFirstAnswer, "alpha", 1f, 0f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvSecondAnswer, "alpha", 1f, 0f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvThirdAnswer, "alpha", 1f, 0f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvFourthAnswer, "alpha", 1f, 0f).apply {
                duration = DURATION
            },
        )

        fadeInList = listOf<ObjectAnimator>(
            ObjectAnimator.ofFloat(binding.tvFirstAnswer, "alpha", 0f, 1f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvSecondAnswer, "alpha", 0f, 1f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvThirdAnswer, "alpha", 0f, 1f).apply {
                duration = DURATION
            },
            ObjectAnimator.ofFloat(binding.tvFourthAnswer, "alpha", 0f, 1f).apply {
                duration = DURATION
            },
        )
    }

    private fun initFadeListener() {
        fadeOutQuestion.addListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    fadeOutList.map {
                        it.start()
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    viewModel.plusStepValue()
                    viewModel.clearAllChecked()

                    fadeInQuestion.start()
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

    private fun initNextBtnClickListener() {
        binding.btnTendencyNext.setOnSingleClickListener {
            when (viewModel.step.value) {
                9 -> moveTendencyTestResultActivity()
                else -> fadeOutQuestion.start()
            }
        }
    }

    private fun moveTendencyTestResultActivity() {
        // 페이지 이동 기능 추가 예정
    }

    companion object {
        const val DURATION = 500L
    }
}
