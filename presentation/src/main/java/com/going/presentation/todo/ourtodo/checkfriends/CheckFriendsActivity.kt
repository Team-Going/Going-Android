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
                is UiState.Success -> {
                    adapter.submitList(state.data.participants)
                    val rate = state.data.styles.map { it.rate }
                    val isLeft = state.data.styles.map { it.isLeft }
                    setProgressBarStatus(rate, isLeft)
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun setProgressBarStatus(rate: List<Int>, isLeft: List<Boolean>) {
        val progressBars = listOf(
            binding.progressBarCheckFriends1,
            binding.progressBarCheckFriends2,
            binding.progressBarCheckFriends3,
            binding.progressBarCheckFriends4,
            binding.progressBarCheckFriends5
        )

        val progressBarsRevert = listOf(
            binding.progressBarCheckFriends1Revert,
            binding.progressBarCheckFriends2Revert,
            binding.progressBarCheckFriends3Revert,
            binding.progressBarCheckFriends4Revert,
            binding.progressBarCheckFriends5Revert
        )

        for (i in rate.indices) {
            if (isLeft[i]) {
                progressBars[i].visibility = View.VISIBLE
                progressBarsRevert[i].visibility = View.INVISIBLE
                progressBars[i].progress = rate[i]
            } else {
                progressBars[i].visibility = View.INVISIBLE
                progressBarsRevert[i].visibility = View.VISIBLE
                progressBarsRevert[i].progress = rate[i]
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

}