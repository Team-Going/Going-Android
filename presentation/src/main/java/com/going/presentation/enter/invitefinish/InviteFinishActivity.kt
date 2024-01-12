package com.going.presentation.enter.invitefinish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.going.presentation.R
import com.going.presentation.databinding.ActivityInviteFinishBinding
import com.going.presentation.enter.entertrip.EnterTripActivity
import com.going.presentation.preferencetag.PreferenceTagActivity
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
            val title = intent.getStringExtra("title")
            val start = intent.getStringExtra("start")
            val end = intent.getStringExtra("end")
            val day = intent.getIntExtra("day", 0)
            Log.d("day", day.toString())

            binding.tvInviteFinishName.text = title
            binding.tvInviteFinishDay.text = "$start - $end"


            if (day > 0) {
                binding.tvInviteFinishDayLeft.text = "D - $day"
            } else binding.tvInviteFinishDayLeft.text = "여행중"
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
            Intent(this, PreferenceTagActivity::class.java).apply {
                startActivity(this)
            }
        }
    }


}

