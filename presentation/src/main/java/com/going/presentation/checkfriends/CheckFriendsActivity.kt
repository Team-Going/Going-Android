package com.going.presentation.checkfriends

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCheckFriendsBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class CheckFriendsActivity :
    BaseActivity<ActivityCheckFriendsBinding>(R.layout.activity_check_friends) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackClickListener()

    }

    private fun initBackClickListener(){
        binding.btnCheckFriendsBack.setOnSingleClickListener {
            finish()
        }
    }



}