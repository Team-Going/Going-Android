package com.going.presentation.setting

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private var quitDialog: SettingQuitDialogFragment ?= null
    private var logoutDialog: SettingLogoutDialogFragment ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProfileClickListener()
        initInquireClickListener()
        initPolicyClickListener()
        initAboutDooripClickListener()
        initLogoutClickListener()
        initQuitClickListener()

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

    private fun initAboutDooripClickListener() {
        binding.btnSettingAboutDooripEnter.setOnSingleClickListener {

        }
    }

    private fun initLogoutClickListener() {
        binding.btnSettingLogoutEnter.setOnSingleClickListener {
            showLogoutAlertDialog()
        }
    }

    private fun initQuitClickListener() {
        binding.btnSettingQuit.setOnSingleClickListener {
            showQuitAlertDialog()
        }
    }

    private fun showLogoutAlertDialog() {
        logoutDialog = SettingLogoutDialogFragment()
        logoutDialog?.show(supportFragmentManager, logoutDialog?.tag)
    }

    private fun showQuitAlertDialog() {
        quitDialog = SettingQuitDialogFragment()
        quitDialog?.show(supportFragmentManager, quitDialog?.tag)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(logoutDialog?.isAdded == true) logoutDialog?.dismiss()
        if(quitDialog?.isAdded == true) quitDialog?.dismiss()
    }

}