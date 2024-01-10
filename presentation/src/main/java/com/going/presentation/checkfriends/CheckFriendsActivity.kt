package com.going.presentation.checkfriends

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCheckFriendsBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class CheckFriendsActivity :
    BaseActivity<ActivityCheckFriendsBinding>(R.layout.activity_check_friends) {

    private var _adapter: CheckFriendsAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<CheckFriendsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackClickListener()
        initAdapter()

    }

    private fun initBackClickListener() {
        binding.btnCheckFriendsBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        _adapter = CheckFriendsAdapter()
        binding.rvCheckFriendsMember.adapter = adapter
        adapter.submitList(viewModel.mockParticipantsList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

}