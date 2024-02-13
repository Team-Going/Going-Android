package com.going.presentation.entertrip.invitetrip.finish

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityInviteFinishBinding
import com.going.presentation.entertrip.createtrip.preference.EnterPreferenceActivity
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.DAY
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.END
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.START
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TITLE
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TRIP_ID
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
            FinishPreferenceActivity.createIntent(
                this,
                tripId
            ).apply { startActivity(this) }
        }
    }

    companion object {
        const val DATE_FORMAT = "%s - %s"
        const val D_DAY_FORMAT = "D-%d"
        const val TRIP_FORMAT = "여행중"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            title: String,
            startDate: String,
            endDate: String,
            day: Int,
        ): Intent = Intent(context, EnterPreferenceActivity::class.java).apply {
            putExtra(TRIP_ID, tripId)
            putExtra(TITLE, title)
            putExtra(START, startDate)
            putExtra(END, endDate)
            putExtra(DAY, day)
        }
    }
}

