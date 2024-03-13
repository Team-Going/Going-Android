package com.going.presentation.todo.ourtodo.invite

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentFriendInviteDialogBinding
import com.going.presentation.todo.ourtodo.checkfriends.CheckFriendsViewModel
import com.going.ui.base.BaseDialog
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class FriendInviteEmptyDialog :
    BaseDialog<FragmentFriendInviteDialogBinding>(R.layout.fragment_friend_invite_dialog) {

    private val viewModel by activityViewModels<CheckFriendsViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInviteCode()
        initExitBtnListener()
        initLinkInviteBtnListener()
    }

    private fun setInviteCode() {
        binding.tvTodoInviteCode.text = viewModel.inviteCode
    }

    private fun initExitBtnListener() {
        binding.btnTodoInviteFinish.setOnSingleClickListener {
            dismiss()
        }
    }

    private fun initLinkInviteBtnListener() {
        binding.tvTodoInviteTermsText.setOnSingleClickListener {
            val clipboardManager =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(CLIP_LABEL, viewModel.inviteCode)
            clipboardManager.setPrimaryClip(clipData)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) toast(getString(R.string.finish_trip_tv_copy_code_complete))
        }
    }

    companion object {
        const val CLIP_LABEL = "RECOMMEND_LINK"
    }
}
