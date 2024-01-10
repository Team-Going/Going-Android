package com.going.presentation.setting

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.going.presentation.R
import com.going.presentation.databinding.FragmentSettingQuitDialogBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener

class SettingQuitDialogFragment :
    BaseDialog<FragmentSettingQuitDialogBinding>(R.layout.fragment_setting_quit_dialog) {

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
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
        }
    }

    private fun initPositiveClickListener() {
        binding.tvDialogPositive.setOnSingleClickListener {
            dismiss()
        }
    }

    private fun initNegativeClickListener() {
        // 탈퇴하기 버튼을 눌렀을 때의 로직
    }

}


