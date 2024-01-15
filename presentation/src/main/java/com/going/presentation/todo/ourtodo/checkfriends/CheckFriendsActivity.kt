package com.going.presentation.todo.ourtodo.checkfriends

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCheckFriendsBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
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
        getTripId()
        observeCheckFriendsListState()
        setProgressBarStatus()

    }

    private fun initBackClickListener() {
        binding.btnCheckFriendsBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        _adapter = CheckFriendsAdapter()
        binding.rvCheckFriendsMember.adapter = adapter
    }

    private fun getTripId() {
        val tripId = intent.getLongExtra("tripId", -1L)
        viewModel.getFriendsListFromServer(tripId)
    }

    private fun observeCheckFriendsListState() {
        viewModel.checkFriendsListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> adapter.submitList(state.data.participants)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun setProgressBarStatus() {
        // styleA의 평균 값이 20이라고 가정
        binding.progressBarCheckFriends1.progress = 20
        if (binding.progressBarCheckFriends1.progress <= 50) {
            binding.progressBarCheckFriends1.progress = 20
            binding.progressBarCheckFriends2.progress = 40
            binding.progressBarCheckFriends3.progress = 50
        }

        // styleD의 평균 값이 60이라고 가정
        binding.progressBarCheckFriends4.progress = 60
        if (binding.progressBarCheckFriends4.progress > 50) {
            // 프로그레스바 반대 방향으로 적용
            binding.progressBarCheckFriends4.visibility = View.INVISIBLE
            binding.progressBarCheckFriends4Revert.visibility = View.VISIBLE

            binding.progressBarCheckFriends5.visibility = View.INVISIBLE
            binding.progressBarCheckFriends5Revert.visibility = View.VISIBLE

            binding.progressBarCheckFriends4Revert.progress = 60
            binding.progressBarCheckFriends5Revert.progress = 70
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

}