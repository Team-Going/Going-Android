package com.going.presentation.tendencytest

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestBinding
import com.going.ui.base.BaseActivity

class TendencyTestActivity :
    BaseActivity<ActivityTendencyTestBinding>(R.layout.activity_tendency_test) {

    private val viewModel by viewModels<TendencyTestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }
}
