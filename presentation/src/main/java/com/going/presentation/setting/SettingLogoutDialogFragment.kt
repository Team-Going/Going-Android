package com.going.presentation.setting

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.going.presentation.R
import com.going.presentation.databinding.FragmentSettingLogoutDialogBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import kotlinx.coroutines.flow.onEach

class SettingLogoutDialogFragment(var navigateToSplashScreen: () -> Unit) :
    BaseDialog<FragmentSettingLogoutDialogBinding>(R.layout.fragment_setting_logout_dialog) {

    private val viewModel by activityViewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNegativeClickListener()
        initPositiveClickListener()
        observeUserSignOutState()
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
            viewModel.signOutKakao()
        }
    }

    private fun observeUserSignOutState() {
        viewModel.userSignOutState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                true -> navigateToSplashScreen()
                false -> toast(getString(R.string.server_error))
                null -> {}
            }
        }
    }

    private fun initNegativeClickListener() {
        binding.btnDialogNegative.setOnSingleClickListener {
            dismiss()
        }
    }
}
