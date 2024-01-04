package com.going.presentation.tendencytest

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencyTestSplashBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencyTestSplashActivity :
    BaseActivity<ActivityTendencyTestSplashBinding>(R.layout.activity_tendency_test_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartBtnSingleClickListener()
    }

    private fun initStartBtnSingleClickListener() {
        binding.btnTendencySplashStart.setOnSingleClickListener {
            // 페이지 이동~
        }
    }
}
