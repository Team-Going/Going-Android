package com.going.presentation.tendencytest

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencyTestActivity :
    BaseActivity<ActivityTendencyTestBinding>(R.layout.activity_tendency_test) {

    private val viewModel by viewModels<TendencyTestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()

        binding.btnTendencyNext.setOnSingleClickListener {
            if (viewModel.step.value != 9) {
                viewModel.stepUp()
                binding.rgAnswers.clearCheck()
                viewModel.isChecked.value = false
            } else {
                // 페이지 이동
            }
        }

        binding.rgAnswers.setOnCheckedChangeListener { group, checkedId ->
            viewModel.isChecked.value = true
        }
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }
}
