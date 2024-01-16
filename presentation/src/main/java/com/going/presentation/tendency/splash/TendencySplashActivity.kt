package com.going.presentation.tendency.splash

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTendencySplashBinding
import com.going.presentation.tendency.ttest.TendencyTestActivity
import com.going.presentation.util.initOnBackPressedListener
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class TendencySplashActivity :
    BaseActivity<ActivityTendencySplashBinding>(R.layout.activity_tendency_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartBtnClickListener()
        initOnBackPressedListener()
    }

    private fun initStartBtnClickListener() {
        binding.btnTendencySplashStart.setOnSingleClickListener {
            navigateToTendencyTestScreen()
        }
    }

    private fun navigateToTendencyTestScreen() {
        Intent(this, TendencyTestActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }
}
