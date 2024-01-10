package com.going.presentation.enter.invitefinish

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityInviteFinishBinding
import com.going.presentation.enter.entertrip.EnterTripActivity
import com.going.presentation.preferencetag.PreferenceTagActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener


class InviteFinishActivity :
    BaseActivity<ActivityInviteFinishBinding>(R.layout.activity_invite_finish) {
    private val viewModel by viewModels<InviteFinishViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnClickListener()
        initNextBtnClickListener()

    }

    private fun initBackBtnClickListener() {
        binding.btnInviteFinishBack.setOnSingleClickListener {
            Intent(this, EnterTripActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initNextBtnClickListener() {
        binding.btnInviteFinishEnter.setOnSingleClickListener {
            Intent(this, PreferenceTagActivity::class.java).apply {
                startActivity(this)
            }
        }
    }


}

