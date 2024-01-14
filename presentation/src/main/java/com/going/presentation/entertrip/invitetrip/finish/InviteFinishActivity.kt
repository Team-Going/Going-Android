package com.going.presentation.entertrip.invitetrip.finish

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityInviteFinishBinding
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.DAY
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.END
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.START
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.TITLE
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.entertrip.invitetrip.preference.FinishPreferenceActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InviteFinishActivity :
    BaseActivity<ActivityInviteFinishBinding>(R.layout.activity_invite_finish) {

    private var tripId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getServerList()
        initBackBtnClickListener()
        initEnterBtnClickListener()

    }

    private fun getServerList() {
        if (intent != null) {
            tripId = intent.getLongExtra(TRIP_ID, -1L)
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
            finish()
        }
    }

    private fun initEnterBtnClickListener() {
        binding.btnInviteFinishEnter.setOnSingleClickListener {
            Intent(this, FinishPreferenceActivity::class.java).apply {
                putExtra(TRIP_ID, tripId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
            finish()
        }
    }

    companion object {
        const val DATE_FORMAT = "%s - %s"
        const val D_DAY_FORMAT = "D - %d"
        const val TRIP_FORMAT = "여행중"
    }
}

