package com.going.presentation.setting

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.going.presentation.R
import com.going.presentation.databinding.ActivitySettingBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private lateinit var settingDialog: SettingCustomDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProfileClickListener()
        initInquireClickListener()
        initPolicyClickListener()
        initAboutDoorip()
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
        settingDialog = SettingCustomDialogFragment()
        settingDialog.show(supportFragmentManager, settingDialog.tag)
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            delay(190)
            window.statusBarColor = ContextCompat.getColor(this@SettingActivity, R.color.transparent_60)
        }
    }

   private fun initQuitClickListener(){
        binding.btnSettingQuit.setOnSingleClickListener {

        }
    }

}