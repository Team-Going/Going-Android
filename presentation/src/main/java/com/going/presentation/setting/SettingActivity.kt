package com.going.presentation.setting

import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.presentation.onboarding.splash.SplashActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private var quitDialog: SettingQuitDialogFragment? = null
    private var logoutDialog: SettingLogoutDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProfileClickListener()
        initInquireClickListener()
        initPolicyClickListener()
        initAboutDooripClickListener()
        initLogoutClickListener()
        initQuitClickListener()
        setVersionCode()
    }

    private fun initProfileClickListener() {
        binding.layoutSettingProfile.setOnSingleClickListener {

        }
    }

    private fun initInquireClickListener() {
        binding.layoutSettingInquire.setOnSingleClickListener {

        }
    }

    private fun initPolicyClickListener() {
        binding.layoutSettingPolicy.setOnSingleClickListener {

        }
    }

    private fun initAboutDooripClickListener() {
        binding.layoutSettingAboutDoorip.setOnSingleClickListener {
        }
    }

    private fun initLogoutClickListener() {
        binding.layoutSettingLogout.setOnSingleClickListener {
            showLogoutAlertDialog()
        }
    }

    private fun initQuitClickListener() {
        binding.btnSettingQuit.setOnSingleClickListener {
            showQuitAlertDialog()
        }
    }

    private fun showLogoutAlertDialog() {
        logoutDialog = SettingLogoutDialogFragment() {
            navigateToSplashScreen()
        }
        logoutDialog?.show(supportFragmentManager, logoutDialog?.tag)
    }

    private fun showQuitAlertDialog() {
        quitDialog = SettingQuitDialogFragment()
        quitDialog?.show(supportFragmentManager, quitDialog?.tag)
    }

    private fun setVersionCode() {
        binding.tvSettingShowServiceVersion.text = VERSION_CODE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (logoutDialog?.isAdded == true) logoutDialog?.dismiss()
        if (quitDialog?.isAdded == true) quitDialog?.dismiss()
    }

    fun navigateToSplashScreen() {
        Intent(this, SplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    companion object {
        private const val VERSION_CODE = "v1.0"
    }
}
