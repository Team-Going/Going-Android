package com.going.presentation.starttrip.finishtrip

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityFinishTripBinding
import com.going.presentation.preferencetag.entertrip.EnterPreferenceActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class FinishTripActivity :
    BaseActivity<ActivityFinishTripBinding>(R.layout.activity_finish_trip) {
    private val viewModel by viewModels<FinishTripViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCopyCodetvClickListener()
        initSendCodeBtnClickListener()
        initEnterTripBtnClickListener()
        //checkDayLeft()
    }

    private fun initCopyCodetvClickListener() {
        binding.tvFinishTripTermsText.setOnSingleClickListener {
            val clipboardManager =
                this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("INVITE_CODE_LABEL", viewModel.INVITE_CODE)
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
            Intent(this, EnterPreferenceActivity::class.java).apply {
                startActivity(this)
                //입장 코드 받아서 보내기
            }
            finish()
        }
    }

//    private fun checkDayLeft(){
//       그 전 뷰에서 서버 붙여서 보내주는 day 값 알게 되면 맞게 로직 구현하겠습니다
//        if(dayLeft > 0) {
//            binding.tvFinishTripDayLeft.text = dayLeft
//        }else{
//            binding.tvFinishTripDayLeft.text =
//        }
//    }
    companion object {
        const val INVITE_CODE_LABEL = "Invite Code"
    }
}

