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
    private lateinit var fadeInAnswer: ObjectAnimator
    private lateinit var fadeOutAnswer: ObjectAnimator

    private val viewModel by viewModels<TendencyTestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        initFadeAnimation()
        initFadeListener()
        initNextBtnClickListener()
        initAnswersClickListener()
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

        fadeOutAnswer = ObjectAnimator.ofFloat(binding.rgAnswers, "alpha", 1f, 0f).apply {
            duration = DURATION
        }

        fadeInAnswer = ObjectAnimator.ofFloat(binding.rgAnswers, "alpha", 0f, 1f).apply {
            duration = DURATION
        }
    }

    private fun initFadeListener() {
        fadeOutQuestion.addListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    fadeOutAnswer.start()
                }

                override fun onAnimationEnd(animation: Animator) {
                    viewModel.stepUp()
                    binding.rgAnswers.clearCheck()
                    viewModel.isChecked.value = false
                    fadeInQuestion.start()
                    fadeInAnswer.start()
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
            if (viewModel.step.value != 9) {
                viewModel.isChecked.value = false
                fadeOutQuestion.start()
            } else {
                // 페이지 이동
            }
        }
    }

    private fun initAnswersClickListener() {
        binding.rgAnswers.setOnCheckedChangeListener { _, checkedId ->
            viewModel.isChecked.value = true

            viewModel.tendencyResultList[viewModel.step.value - 1] = when (checkedId) {
                R.id.rb_first_answer -> 1
                R.id.rb_second_answer -> 2
                R.id.rb_third_answer -> 3
                R.id.rb_fourth_answer -> 4
                else -> 0
            }
        }
    }

    companion object {
        const val DURATION = 500L
    }
}
