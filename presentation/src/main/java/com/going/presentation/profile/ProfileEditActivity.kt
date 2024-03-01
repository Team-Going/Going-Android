package com.going.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityProfileEditBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditActivity :
    BaseActivity<ActivityProfileEditBinding>(R.layout.activity_profile_edit) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setEtNameArguments()
        setEtInfoArguments()
        getUserInfo()
        initBackBtnClickListener()
    }

    private fun setEtNameArguments() {
        with(binding.etProfileEditNickname) {
            setMaxLen(3)
            overWarning = getString(R.string.name_over_error)
            blankWarning = getString(R.string.name_blank_error)
        }
    }

    private fun setEtInfoArguments() {
        with(binding.etProfileEditInfo) {
            setMaxLen(20)
            overWarning = getString(R.string.info_over_error)
        }
    }

    private fun getUserInfo() {
        binding.etProfileEditNickname.editText.setText(intent.getStringExtra(NICKNAME))
        binding.etProfileEditInfo.editText.setText(intent.getStringExtra(INFO))
    }

    private fun initBackBtnClickListener() {
        binding.btnProfileEditBack.setOnSingleClickListener {
            finish()
        }
    }

    companion object {
        const val NICKNAME = "NICKNAME"
        const val INFO = "INFO"

        @JvmStatic
        fun createIntent(
            context: Context,
            nickName: String,
            info: String,
        ): Intent = Intent(context, ProfileEditActivity::class.java).apply {
            putExtra(NICKNAME, nickName)
            putExtra(INFO, info)
        }
    }
}