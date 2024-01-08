package com.going.presentation.tripdashboard.triplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.domain.entity.response.CompletedListModel
import com.going.domain.entity.response.OngoingListModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentCompletedTripBinding
import com.going.presentation.tripdashboard.TripDashBoardViewModel
import com.going.ui.base.BaseFragment

class CompletedTripFragment : BaseFragment<FragmentCompletedTripBinding>(R.layout.fragment_completed_trip),
CompletedAdapter.OnDashBoardSelectedListener{

    private val viewModel by activityViewModels<TripDashBoardViewModel>()

    private var _adapter: CompletedAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

    }

    override fun onDashBoardSelectedListener(tripCreate: CompletedListModel) {
        // 여행 생성 레이아웃 클릭 시 처리
    }

    private fun setRecyclerView() {
        _adapter = CompletedAdapter(this)
        binding.rvDashboardCompletedTrip.adapter = adapter
        adapter.submitList(viewModel.mockCompletedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}