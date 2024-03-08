package com.going.presentation.profile.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityProfileEditBinding
import com.going.presentation.designsystem.snackbar.customSnackBar
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileEditActivity :
    BaseActivity<ActivityProfileEditBinding>(R.layout.activity_profile_edit) {
    private val viewModel by viewModels<ProfileEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setEtNameArguments()
        setEtInfoArguments()
        getUserInfo()
        observeNameTextChanged()
        observeInfoTextChanged()
        observeIsValueChanged()
        initBackBtnClickListener()
        initProfileEditBtnClickListener()
        observeIsChangedSuccess()
    }

    private fun setEtNameArguments() {
        with(binding.etProfileEditNickname) {
            setMaxLen(viewModel.getMaxNameLen())
            overWarning = getString(R.string.name_over_error)
            blankWarning = getString(R.string.name_blank_error)
        }
    }

    private fun setEtInfoArguments() {
        with(binding.etProfileEditInfo) {
            setMaxLen(viewModel.getMaxInfoLen())
            overWarning = getString(R.string.info_over_error)
        }
    }

    private fun observeNameTextChanged() {
        binding.etProfileEditNickname.editText.doAfterTextChanged { name ->
            viewModel.checkIsNameChanged(name.toString())
        }
    }

    private fun observeInfoTextChanged() {
        binding.etProfileEditInfo.editText.doAfterTextChanged { info ->
            viewModel.checkIsInfoChanged(info.toString())
        }
    }

    private fun getUserInfo() {
        val name = intent.getStringExtra(NICKNAME)
        val info = intent.getStringExtra(INFO)

        with(binding) {
            etProfileEditNickname.editText.setText(name)
            etProfileEditInfo.editText.setText(info)
        }
        viewModel.setDefaultValues(name.orEmpty(), info.orEmpty())
    }

    private fun observeIsValueChanged() {
        viewModel.isValueChanged.flowWithLifecycle(lifecycle).onEach { state ->
            binding.btnProfileEditFinish.isEnabled = state
        }.launchIn(lifecycleScope)
    }

    private fun initBackBtnClickListener() {
        binding.btnProfileEditBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initProfileEditBtnClickListener() {
        binding.btnProfileEditFinish.setOnSingleClickListener {
            viewModel.patchUserInfo()
        }
    }

    private fun observeIsChangedSuccess() {
        viewModel.isChangedSuccess.flowWithLifecycle(lifecycle).onEach {
            if (it) {
                toast(getString(R.string.edit_profile_finish))
                finish()
            } else customSnackBar(binding.root, getString(R.string.server_error))
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val NICKNAME = "NICKNAME"
        private const val INFO = "INFO"

        @JvmStatic
        fun createIntent(
            context: Context,
            nickName: String,
            info: String,
        ): Intent = Intent(context, ProfileEditActivity::class.java).apply {
            putExtra(NICKNAME, nickName)
            putExtra(INFO, info)
        }
    }
}