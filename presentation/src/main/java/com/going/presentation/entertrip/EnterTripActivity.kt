package com.going.presentation.entertrip

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.domain.entity.CodeState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterTripBinding
import com.going.presentation.starttrip.StartTripSplashActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class EnterTripActivity :
    BaseActivity<ActivityEnterTripBinding>(R.layout.activity_enter_trip) {
    private val viewModel by viewModels<EnterTripViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnClickListener()
        initBindingViewModel()
        observeIsCodeAvailable()
        initNextBtnClickListener()

    }

    private fun initBackBtnClickListener() {
        binding.btnEnterBack.setOnSingleClickListener {
            Intent(this, StartTripSplashActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun observeIsCodeAvailable() {
        viewModel.isCodeAvailable.observe(this) { state ->
            setColors(
                false,
                viewModel.codeLength.value ?:0,
                binding.tvCodeCounter,
            ) { background ->
                binding.etEnterTripName.background = ResourcesCompat.getDrawable(
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
            viewModel.isCodeAvailable.value != CodeState.Blank && hasFocus -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            length == 0 -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            (viewModel.isCodeAvailable.value == CodeState.Blank || viewModel.isCodeAvailable.value == CodeState.Invalid) && counter == binding.tvCodeCounter -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            else -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
        }
        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }

    private fun initNextBtnClickListener() {
        binding.btnEnterTripNext.setOnSingleClickListener {
            //다음 뷰로 이동
        }
    }

}