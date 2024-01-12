package com.going.presentation.dashboard.triplist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardViewModel
import com.going.presentation.databinding.FragmentOngoingTripBinding
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OngoingTripFragment :
    BaseFragment<FragmentOngoingTripBinding>(R.layout.fragment_ongoing_trip),
    OngoingAdapter.OnDashBoardSelectedListener {

    private val viewModel by activityViewModels<DashBoardViewModel>()

    private var _adapter: OngoingAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initItemDecoration()
        setTripList()
        observeDashBoardListState()

    }


    private fun initAdapter() {
        _adapter = OngoingAdapter(this)
        binding.rvDashboardOngoingTrip.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = DashBoardDecoration(requireContext())
        binding.rvDashboardOngoingTrip.addItemDecoration(itemDeco)
    }

    private fun setTripList() {
        val progress = DashBoardViewModel.ONGOING
        viewModel.getTripListFromServer(progress)
    }

    private fun observeDashBoardListState() {
        viewModel.dashBoardOngoingListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    setEmptyView(false)
                    adapter.submitList(state.data.trips)
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> setEmptyView(true)
            }
        }.launchIn(lifecycleScope)
    }

    private fun setEmptyView(isEmpty: Boolean) {
        binding.rvDashboardOngoingTrip.isVisible = !isEmpty
        binding.layoutDashboardEmpty.isVisible = isEmpty
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

    override fun onDashBoardSelectedListener(tripCreate: DashBoardTripModel) {

    }

}