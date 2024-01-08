package com.going.presentation.starttrip

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityStartTripSplashBinding
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
        //초대 코드 받아서 여행 입장하는 액티비티로 연결
        }
    }
}