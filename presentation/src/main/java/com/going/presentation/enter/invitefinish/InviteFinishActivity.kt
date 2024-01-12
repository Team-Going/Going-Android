package com.going.presentation.enter.invitefinish

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityInviteFinishBinding
import com.going.presentation.enter.entertrip.EnterTripActivity
import com.going.presentation.enter.entertrip.EnterTripActivity.Companion.DAY
import com.going.presentation.enter.entertrip.EnterTripActivity.Companion.END
import com.going.presentation.enter.entertrip.EnterTripActivity.Companion.START
import com.going.presentation.enter.entertrip.EnterTripActivity.Companion.TITLE
import com.going.presentation.preferencetag.entertrip.EnterPreferenceActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InviteFinishActivity :
    BaseActivity<ActivityInviteFinishBinding>(R.layout.activity_invite_finish) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getServerList()
        initBackBtnClickListener()
        initEnterBtnClickListener()

    }

    private fun getServerList() {
        val serverlist = getIntent()

        if (serverlist != null) {
            val title = intent.getStringExtra(TITLE)
            val start = intent.getStringExtra(START)
            val end = intent.getStringExtra(END)
            val day = intent.getIntExtra(DAY, 0)

            binding.tvInviteFinishName.text = title
            binding.tvInviteFinishDay.text = String.format(DATE_FORMAT, start, end)

            if (day > 0) {
                binding.tvInviteFinishDayLeft.text = String.format(D_DAY_FORMAT, day)
            } else {
                binding.tvInviteFinishDayLeft.text = TRIP_FORMAT
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnInviteFinishBack.setOnSingleClickListener {
            Intent(this, EnterTripActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initEnterBtnClickListener() {
        binding.btnInviteFinishEnter.setOnSingleClickListener {
            Intent(this, FinishPreferenceActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    companion object {
        const val DATE_FORMAT = "%s - %s"
        const val D_DAY_FORMAT = "D - %d"
        const val TRIP_FORMAT = "여행중"
    }


}

