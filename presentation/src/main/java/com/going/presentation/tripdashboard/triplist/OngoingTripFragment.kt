package com.going.presentation.tripdashboard.triplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOngoingTripBinding
import com.going.presentation.tripdashboard.TripDashBoardAdapter
import com.going.presentation.tripdashboard.TripDashBoardViewModel
import com.going.ui.base.BaseFragment

class OngoingTripFragment :
    BaseFragment<FragmentOngoingTripBinding>(R.layout.fragment_ongoing_trip),
    TripDashBoardAdapter.OnDashBoardSelectedListener {

    private var _adapter: TripDashBoardAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<TripDashBoardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

    }

    override fun onDashBoardSelectedListener(tripCreate: TripCreateListModel) {
        // 여행 생성 레이아웃 클릭 시 처리
    }

    private fun initAdapter() {
        _adapter = TripDashBoardAdapter(this)
        binding.rvDashboardOngoingTrip.adapter = adapter
        adapter.submitList(viewModel.mockDataList)
    }

}