package com.going.presentation.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.presentation.profile.ProfileActivity
import com.going.presentation.profile.ProfileViewModel
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val profileViewModel by viewModels<ProfileViewModel>()

    private var quitDialog: SettingQuitDialogFragment? = null
    private var logoutDialog: SettingLogoutDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnClickListener()
        initProfileClickListener()
        initInquireClickListener()
        setVersionCode()
        initPolicyClickListener()
        initTermsClickListener()
        initAboutDooripClickListener()
        initLogoutClickListener()
        initQuitClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnPreferenceBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initProfileClickListener() {
        binding.btnSettingProfile.setOnSingleClickListener {
            Intent(this, ProfileActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initInquireClickListener() {
        binding.btnSettingInquire.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(FAQ)).apply {
                startActivity(this)
            }
        }
    }

    private fun setVersionCode() {
        binding.tvSettingShowServiceVersion.text = VERSION_CODE
    }

    private fun initPolicyClickListener() {
        binding.btnSettingPolicy.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL)).apply {
                startActivity(this)
            }
        }
    }

    private fun initTermsClickListener() {
        binding.btnSettingTerms.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_URL)).apply {
                startActivity(this)
            }
        }
    }

    private fun initAboutDooripClickListener() {
        binding.btnSettingAboutDoorip.setOnSingleClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(ABOUT_DOORIP_URL)).apply {
                startActivity(this)
            }
        }
    }

    private fun initLogoutClickListener() {
        binding.btnSettingLogout.setOnSingleClickListener {
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
        if (logoutDialog?.isAdded == true) logoutDialog?.dismiss()
        if (quitDialog?.isAdded == true) quitDialog?.dismiss()
    }

    companion object {
        private const val VERSION_CODE = "v1.1"
        private const val FAQ =
            "https://goinggoing.notion.site/FAQ-920f6ad93fea46a983061f412e15cad1?pvs=74"
        private const val PRIVACY_POLICY_URL =
            "https://goinggoing.notion.site/c4d5513bba2c4c20aaf9e21522289304?pvs=74"
        const val TERMS_URL =
            "https://goinggoing.notion.site/75f5d981a5b842a6be74a9dc17ca67de?pvs=74"
        private const val ABOUT_DOORIP_URL =
            "https://goinggoing.notion.site/758273e2bebb477aac0adb0195359f21"
    }
}
