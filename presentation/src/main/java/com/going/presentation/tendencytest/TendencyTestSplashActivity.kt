package com.going.presentation.tendencytest

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestSplashBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencyTestSplashActivity :
    BaseActivity<ActivityTendencyTestSplashBinding>(R.layout.activity_tendency_test_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartBtnClickListener()
    }

    private fun initStartBtnClickListener() {
        binding.btnTendencySplashStart.setOnSingleClickListener {
            navigateToTendencyTestScreen()
        }
    }

    private fun navigateToTendencyTestScreen() {
        Intent(this, TendencyTestActivity::class.java).apply {
            startActivity(this)
        }
    }
}
