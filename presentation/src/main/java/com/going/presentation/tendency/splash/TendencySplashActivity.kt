package com.going.presentation.tendency.splash

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityTendencySplashBinding
import com.going.presentation.tendency.ttest.TendencyTestActivity
import com.going.presentation.util.initOnBackPressedListener
import com.going.presentation.util.navigateToScreenClear
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencySplashActivity :
    BaseActivity<ActivityTendencySplashBinding>(R.layout.activity_tendency_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSkipBtnClickListener()
        initStartBtnClickListener()
        initOnBackPressedListener(binding.root)
    }

    private fun initSkipBtnClickListener() =
        binding.btnTendencySplashSkip.setOnSingleClickListener {
            navigateToScreenClear<DashBoardActivity>()
        }

    private fun initStartBtnClickListener() =
        binding.btnTendencySplashStart.setOnSingleClickListener {
            navigateToScreenClear<TendencyTestActivity>()
        }
}
