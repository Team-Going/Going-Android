package com.going.presentation.setting


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.going.presentation.R
import com.going.presentation.databinding.FragmentCustomDialogSettingBinding
import com.going.ui.base.BaseDialog


class CustomDialogSettingFragment :
    BaseDialog<FragmentCustomDialogSettingBinding>(R.layout.fragment_custom_dialog_setting) {

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
            )
            setBackgroundDrawableResource(R.color.transparent_50)
            setStatusBarTransparent()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            // 탈퇴 버튼, 남아있기 버튼 눌렀을 때의 처리
    }

    private fun setStatusBarTransparent() {

    }
}


