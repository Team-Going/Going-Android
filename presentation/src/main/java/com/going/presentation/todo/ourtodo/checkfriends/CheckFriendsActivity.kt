package com.going.presentation.todo.ourtodo.checkfriends

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.CheckFriendsModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCheckFriendsBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
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
        setResultTextColor()

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
        val tripId = intent.getLongExtra(EXTRA_TRIP_ID, -1L)
        viewModel.getFriendsListFromServer(tripId)
    }

    private fun observeCheckFriendsListState() {
        viewModel.checkFriendsListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> setFriendsData(state.data)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun setFriendsData(data: CheckFriendsModel) {
        adapter.submitList(data.participants)
        val rate = data.styles.map { it.rate }
        setProgressBarStatus(rate)
    }

    private fun setProgressBarStatus(rate: List<Int>) {

        val progressBars = listOf(
            binding.progressBarCheckFriends1,
            binding.progressBarCheckFriends2,
            binding.progressBarCheckFriends3,
            binding.progressBarCheckFriends4,
            binding.progressBarCheckFriends5
        )

        for (i in rate.indices) {
            progressBars[i].progress = rate[i]
        }

    }

    private fun setResultTextColor() {
        binding.tvCheckFriendsResult.apply {
            text = SpannableStringBuilder(text).apply {
                setSpan(
                    ForegroundColorSpan(
                        colorOf(R.color.red_500)
                    ), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

}