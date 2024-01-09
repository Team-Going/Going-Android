package com.going.presentation.setting

import android.app.AlertDialog
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.skydoves.balloon.balloon

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProfileClickListener()
        initInquireClickListener()
        initPolicyClickListener()
        initAboutDoorip()
        initLogoutClickListener()

    }

    private fun initProfileClickListener(){
        binding.btnSettingProfileEnter.setOnSingleClickListener {

        }
    }

    private fun initInquireClickListener(){
        binding.btnSettingInquireEnter.setOnSingleClickListener {

        }
    }

    private fun initPolicyClickListener(){
        binding.btnSettingPolicyEnter.setOnSingleClickListener {

        }
    }

    private fun initAboutDoorip(){
        binding.btnSettingAboutDooripEnter.setOnSingleClickListener {

        }
    }

    private fun initLogoutClickListener(){
        binding.btnSettingLogoutEnter.setOnSingleClickListener {
            showLogoutAlertDialog()
        }
    }
    private fun showLogoutAlertDialog() =
        AlertDialog.Builder(this)
            .setTitle("정말 탈퇴하시겠어요?")
            .setMessage("탈퇴시, 정보가 모두 없어져요.")
            .    setNegativeButton("탈퇴하기",
                { dialog, id ->
                    // User cancelled the dialog
                })
            .setPositiveButton(
                "남아있기",
            ) { _, _ ->
                finishAffinity()
            }
            .create()
            .show()

}