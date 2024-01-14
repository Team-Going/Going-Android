package com.going.presentation.entertrip.createtrip.finish

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityFinishTripBinding
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.DAY
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.END
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.START
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.TITLE
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity.Companion.DATE_FORMAT
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity.Companion.D_DAY_FORMAT
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity.Companion.TRIP_FORMAT
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.kakao.sdk.auth.Constants.CODE

class FinishTripActivity :
    BaseActivity<ActivityFinishTripBinding>(R.layout.activity_finish_trip) {

    private var inviteCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTripInfo()
        initCopyCodetvClickListener()
        initSendCodeBtnClickListener()
        initEnterTripBtnClickListener()

    }

    private fun initCopyCodetvClickListener() {
        binding.tvFinishTripTermsText.setOnSingleClickListener {
            val clipboardManager =
                this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("INVITE_CODE_LABEL", inviteCode)
            clipboardManager.setPrimaryClip(clipData)
        }
    }

    private fun initSendCodeBtnClickListener() {
        binding.btnFinishTripSendCode.setOnSingleClickListener {
            // TODO : 카카오톡으로 초대코드 보내기
        }
    }

    private fun initEnterTripBtnClickListener() {
        // TODO : 아워투두 화면으로 보내기
        binding.btnFinishTripEnterTrip.setOnSingleClickListener {
            Intent(this, DashBoardActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
            finish()
        }
    }

    private fun getTripInfo() {
        if (intent != null) {
            val title = intent.getStringExtra(TITLE)
            val start = intent.getStringExtra(START)
            val end = intent.getStringExtra(END)
            val code = intent.getStringExtra(CODE)
            val day = intent.getIntExtra(DAY, 0)

            binding.tvFinishTripName.text = title
            binding.tvFinishTripDay.text = String.format(DATE_FORMAT, start, end)
            binding.tvInviteCode.text = code

            if (day > 0) {
                binding.tvFinishTripDayLeft.text = String.format(D_DAY_FORMAT, day)
            } else {
                binding.tvFinishTripDayLeft.text = TRIP_FORMAT
            }
        }
    }

}

