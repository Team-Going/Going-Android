package com.going.presentation.setting

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.skydoves.balloon.balloon

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private lateinit var settingDialog: CustomDialogSettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProfileClickListener()
        initInquireClickListener()
        initPolicyClickListener()
        initAboutDoorip()
        initLogoutClickListener()

    }

    private fun initProfileClickListener() {
        binding.btnSettingProfileEnter.setOnSingleClickListener {

        }
    }

    private fun initInquireClickListener() {
        binding.btnSettingInquireEnter.setOnSingleClickListener {

        }
    }

    private fun initPolicyClickListener() {
        binding.btnSettingPolicyEnter.setOnSingleClickListener {

        }
    }

    private fun initAboutDoorip() {
        binding.btnSettingAboutDooripEnter.setOnSingleClickListener {

        }
    }

    private fun initLogoutClickListener() {
        binding.btnSettingLogoutEnter.setOnSingleClickListener {
            showLogoutAlertDialog()
        }
    }

    private fun showLogoutAlertDialog() {
        settingDialog = CustomDialogSettingFragment()
        settingDialog.show(supportFragmentManager, settingDialog.tag )
    }

}