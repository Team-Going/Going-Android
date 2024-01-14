package com.going.presentation.entertrip

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityStartTripSplashBinding
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity
import com.going.presentation.entertrip.invitetrip.invitecode.CreateTripActivity
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
        }
    }

    private fun initEnterTripBtnClickListener() {
        binding.btnStartTripSplashEnterTrip.setOnSingleClickListener {
            Intent(this, EnterTripActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}
