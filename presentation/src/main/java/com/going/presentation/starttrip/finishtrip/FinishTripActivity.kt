package com.going.presentation.starttrip.finishtrip

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityFinishTripBinding
import com.going.presentation.preferencetag.PreferenceTagActivity
import com.going.presentation.starttrip.createtrip.CreateTripViewModel
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class FinishTripActivity :
    BaseActivity<ActivityFinishTripBinding>(R.layout.activity_finish_trip) {
    private val viewModel by viewModels<FinishTripViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCopyCodetvClickListener()
        initSendCodeBtnClickListener()
        initEnterTripBtnClickListener()
    }

    private fun initCopyCodetvClickListener() {
        val inviteCode =  viewModel.INVITE_CODE

        binding.tvFinishTripTermsText.setOnSingleClickListener {
            val clipboardManager =
                this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Invite Code", inviteCode)
            clipboardManager.setPrimaryClip(clipData)
        }
    }

    private fun initSendCodeBtnClickListener() {
        binding.btnFinishTripSendCode.setOnSingleClickListener {
            //카카오톡으로 초대코드 보내기
        }
    }

    private fun initEnterTripBtnClickListener() {
        binding.btnFinishTripEnterTrip.setOnSingleClickListener {
            Intent(this, PreferenceTagActivity::class.java).apply {
                startActivity(this)
                //입장 코드 받아서 보내기
            }
            finish()
        }
    }
}


