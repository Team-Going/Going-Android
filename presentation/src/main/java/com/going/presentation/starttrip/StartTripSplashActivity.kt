package com.going.presentation.starttrip

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityStartTripSplashBinding
import com.going.presentation.enter.entertrip.EnterTripActivity
import com.going.presentation.starttrip.createtrip.CreateTripActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class StartTripSplashActivity :
    BaseActivity<ActivityStartTripSplashBinding>(R.layout.activity_start_trip_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNewTripBtnClickListener()
        initEnterTripBtnClickListener()
    }

    private fun initNewTripBtnClickListener() {
        binding.btnStartTripSplashNewTrip.setOnSingleClickListener {
            Intent(this, CreateTripActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }

    private fun initEnterTripBtnClickListener() {
        binding.btnStartTripSplashEnterTrip.setOnSingleClickListener {
            Intent(this, EnterTripActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }
}
