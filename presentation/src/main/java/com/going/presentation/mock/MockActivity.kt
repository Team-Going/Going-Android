package com.going.presentation.mock

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityMockBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MockActivity() : BaseActivity<ActivityMockBinding>(R.layout.activity_mock) {

    private var _adapter: MockAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<MockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        setFollowerList()
        observeFollowerListState()
    }

    private fun initAdapter() {
        _adapter = MockAdapter()
        binding.rvFollower.adapter = adapter
    }

    private fun setFollowerList() {
        viewModel.getFollowerListFromServer(0)
    }

    private fun observeFollowerListState() {
        viewModel.followerListState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> adapter.submitList(state.data)

                    is UiState.Failure -> toast(getString(R.string.server_error))

                    is UiState.Loading -> return@onEach

                    is UiState.Empty -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

}