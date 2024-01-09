package com.going.presentation.setting

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.going.presentation.R
import com.going.presentation.databinding.FragmentCustomDialogSettingBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingCustomDialogFragment :
    BaseDialog<FragmentCustomDialogSettingBinding>(R.layout.fragment_custom_dialog_setting) {

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
            val scopeDialog = CoroutineScope(Dispatchers.Main)
            scopeDialog.launch {
                delay(500)
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.white_000)
                dismiss()
            }
        }
    }

    private fun initNegativeClickListener() {

    }

}


