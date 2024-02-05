package com.going.presentation.tendency.ttest

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestBinding
import com.going.presentation.tendency.result.TendencyResultActivity
import com.going.presentation.util.navigateToScreen
import com.going.ui.base.BaseActivity
import com.going.ui.extension.EnumUiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
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
        fadeOutList = listOf(
            createFadeOutAnimator(binding.tvFirstAnswer),
            createFadeOutAnimator(binding.tvSecondAnswer),
            createFadeOutAnimator(binding.tvThirdAnswer),
            createFadeOutAnimator(binding.tvFourthAnswer),
        )

        fadeInList = listOf(
            createFadeInAnimator(binding.tvFirstAnswer),
            createFadeInAnimator(binding.tvSecondAnswer),
            createFadeInAnimator(binding.tvThirdAnswer),
            createFadeInAnimator(binding.tvFourthAnswer),
        )
    }

    private fun createFadeOutAnimator(view: View): ObjectAnimator =
        ObjectAnimator.ofFloat(view, ALPHA, 1f, 0f).apply {
            duration = DURATION
        }

    private fun createFadeInAnimator(view: View): ObjectAnimator =
        ObjectAnimator.ofFloat(view, ALPHA, 0f, 1f).apply {
            duration = DURATION
        }

    private fun initFadeListener() {
        fadeOutList[0].addListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    viewModel.clearAllChecked()
                    setProgressAnimate(binding.pbTendencyTest, viewModel.step.value + 1)

                    fadeOutList.drop(1).forEach { it.start() }
                }

                override fun onAnimationEnd(animation: Animator) {
                    viewModel.plusStepValue()

                    fadeInList.map {
                        it.start()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
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
                LAST_QUESTION -> viewModel.submitTendencyTest()
                else -> fadeOutList[0].start()
            }
        }
    }

    private fun observeButtonSelected() {
        observeCheckedState(viewModel.isFirstChecked, binding.tvFirstAnswer)
        observeCheckedState(viewModel.isSecondChecked, binding.tvSecondAnswer)
        observeCheckedState(viewModel.isThirdChecked, binding.tvThirdAnswer)
        observeCheckedState(viewModel.isFourthChecked, binding.tvFourthAnswer)
    }

    private fun observeCheckedState(isCheckedState: Flow<Boolean>, textView: TextView) {
        isCheckedState.flowWithLifecycle(lifecycle).onEach { isChecked ->
            textView.setTextAppearance(setFont(isChecked))
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
                EnumUiState.SUCCESS -> navigateToScreen<TendencyResultActivity>(listOf(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                EnumUiState.FAILURE -> toast(getString(R.string.server_error))
                else -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        private const val DURATION = 500L

        private const val LAST_QUESTION = 9

        private const val PROGRESS = "progress"
        private const val ALPHA = "alpha"
    }
}
