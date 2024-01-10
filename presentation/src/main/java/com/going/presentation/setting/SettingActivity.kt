package com.going.presentation.setting

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private lateinit var quitDialog: SettingQuitDialogFragment
    private lateinit var logoutDialog: SettingLogoutDialogFragment

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
        logoutDialog.show(supportFragmentManager, logoutDialog.tag)
    }

    private fun showQuitAlertDialog() {
        quitDialog = SettingQuitDialogFragment()
        quitDialog.show(supportFragmentManager, quitDialog.tag)
    }

}