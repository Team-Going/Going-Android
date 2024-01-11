package com.going.presentation.dashboard.triplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.DashBoardModel
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardViewModel
import com.going.presentation.databinding.FragmentCompletedTripBinding
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

        setRecyclerView()
        initItemDecoration()
        setTripList()
        observeDashBoardListState()

    }

    private fun setRecyclerView() {
        _adapter = CompletedAdapter(this)
        binding.rvDashboardCompletedTrip.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = DashBoardDecoration(requireContext())
        binding.rvDashboardCompletedTrip.addItemDecoration(itemDeco)
    }

    private fun setTripList() {
        val progress = "complete"
        viewModel.getTripListFromServer(progress)
    }

    private fun observeDashBoardListState() {
        viewModel.dashBoardCompletedListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> adapter.submitList(state.data.trips)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

    override fun onDashBoardSelectedListener(tripCreate: DashBoardModel.DashBoardTripModel) {

    }

}