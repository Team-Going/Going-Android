package com.going.presentation.tendency.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
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
    private val tendencySplashViewModel by viewModels<TendencySplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setQuarter()
        setBtnString()
        initSkipBtnClickListener()
        initStartBtnClickListener()
        initOnBackPressedListen()
    }

    private fun setQuarter() =
        tendencySplashViewModel.setQuarters(
            intent.getStringExtra(QUARTER) ?: ""
        )

    private fun setBtnString() {
        binding.btnTendencySplashSkip.text = when (tendencySplashViewModel.quarter) {
            TENDENCY -> getString(R.string.tendency_splash_skip_btn)
            PROFILE -> getString(R.string.tendency_splash_profile_skip_btn)
            else -> getString(R.string.tendency_splash_skip_btn)
        }
    }

    private fun initSkipBtnClickListener() =
        binding.btnTendencySplashSkip.setOnSingleClickListener {
            when (tendencySplashViewModel.quarter) {
                TENDENCY -> navigateToScreenClear<DashBoardActivity>()
                PROFILE -> finish()
                else -> finish()
            }
        }

    private fun initStartBtnClickListener() =
        binding.btnTendencySplashStart.setOnSingleClickListener {
            navigateToScreenClear<TendencyTestActivity>()
        }


    private fun initOnBackPressedListen() {
        if (tendencySplashViewModel.quarter == TENDENCY) initOnBackPressedListener(binding.root)
    }

    companion object {
        const val TENDENCY = "TENDENCY"
        const val PROFILE = "PROFILE"
        private const val QUARTER = "QUARTER"

        @JvmStatic
        fun createIntent(
            context: Context,
            quarter: String,
        ): Intent = Intent(context, TendencySplashActivity::class.java).apply {
            putExtra(QUARTER, quarter)
        }
    }
}
