package com.going.presentation.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentSettingQuitDialogBinding
import com.going.ui.base.BaseDialog
import com.going.ui.extension.EnumUiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SettingQuitDialogFragment :
    BaseDialog<FragmentSettingQuitDialogBinding>(R.layout.fragment_setting_quit_dialog) {

    private val viewModel by activityViewModels<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNegativeClickListener()
        initPositiveClickListener()
        observeUserWithDrawState()
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
        binding.btnDialogNegative.setOnSingleClickListener {
            viewModel.startWithDrawKakao()
        }
    }

    private fun observeUserWithDrawState() {
        viewModel.userWithDrawState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                EnumUiState.SUCCESS -> restartApp(requireContext())
                EnumUiState.FAILURE -> toast(getString(R.string.server_error))
                EnumUiState.LOADING -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun restartApp(context: Context) {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val componentName = packageManager.getLaunchIntentForPackage(packageName)?.component
        context.startActivity(Intent.makeRestartActivityTask(componentName))
        Runtime.getRuntime().exit(0)
    }
}
