package com.going.presentation.setting

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.going.presentation.R
import com.going.presentation.databinding.FragmentSettingLogoutDialogBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener

class SettingLogoutDialogFragment :
    BaseDialog<FragmentSettingLogoutDialogBinding>(R.layout.fragment_setting_logout_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNegativeClickListener()
        initPositiveClickListener()

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
            )
            setBackgroundDrawableResource(R.color.transparent_60)
        }
    }

    private fun initPositiveClickListener() {
        binding.tvDialogPositive.setOnSingleClickListener {
            // 로그아웃 버튼 눌렀을 때의 로직
        }
    }

    private fun initNegativeClickListener() {
        binding.btnDialogNegative.setOnSingleClickListener {
            dismiss()
        }
    }

}
