package com.going.presentation.dashboard.triplist.completed

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardViewModel
import com.going.presentation.dashboard.triplist.DashBoardDecoration
import com.going.presentation.dashboard.triplist.ongoing.OngoingTripFragment
import com.going.presentation.databinding.FragmentCompletedTripBinding
import com.going.presentation.entertrip.StartTripSplashActivity
import com.going.presentation.todo.TodoActivity
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CompletedTripFragment :
    BaseFragment<FragmentCompletedTripBinding>(R.layout.fragment_completed_trip),
    CompletedAdapter.OnDashBoardSelectedListener {

    private val viewModel by activityViewModels<DashBoardViewModel>()

    private var _adapter: CompletedAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initItemDecoration()
        observeDashBoardListState()

    }

    private fun initAdapter() {
        _adapter = CompletedAdapter(this)
        binding.rvDashboardCompletedTrip.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = DashBoardDecoration(requireContext())
        binding.rvDashboardCompletedTrip.addItemDecoration(itemDeco)
    }

    private fun setTripList() {
        val progress = DashBoardViewModel.COMPLETED
        viewModel.getTripListFromServer(progress)
    }

    private fun observeDashBoardListState() {
        viewModel.dashBoardCompletedListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    setEmptyView(false)
                    adapter.submitList(state.data.trips)
                    setTripView()
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> setEmptyView(true)

            }
        }.launchIn(lifecycleScope)
    }

    private fun setEmptyView(isEmpty: Boolean) {
        binding.rvDashboardCompletedTrip.isVisible = isEmpty
        binding.layoutDashboardEmpty.isVisible = !isEmpty
    }

    private fun setTripView() {
        binding.rvDashboardCompletedTrip.isVisible = false
        binding.layoutDashboardEmpty.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        setTripList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

    override fun onDashBoardSelectedListener(tripCreate: DashBoardTripModel) {
        Intent(activity, TodoActivity::class.java).apply {
            putExtra(EXTRA_TRIP_ID, tripCreate.tripId)
            startActivity(this)
        }
    }

}