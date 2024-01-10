package com.going.presentation.setting

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.going.presentation.R
import com.going.presentation.databinding.FragmentSettingCustomDialogBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener

class SettingCustomDialogFragment :
    BaseDialog<FragmentSettingCustomDialogBinding>(R.layout.fragment_setting_custom_dialog) {

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
            activity?.window?.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.white_000)
            dismiss()
        }
    }

    private fun initNegativeClickListener() {
        // 탈퇴하기 버튼을 눌렀을 때의 처리
    }

}


