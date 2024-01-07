package com.going.presentation.tripdashboard.triplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentCompletedTripBinding
import com.going.presentation.tripdashboard.TripDashBoardViewModel
import com.going.ui.base.BaseFragment

class CompletedTripFragment : BaseFragment<FragmentCompletedTripBinding>(R.layout.fragment_completed_trip),
TripListAdapter.OnDashBoardSelectedListener{

    private val viewModel by activityViewModels<TripDashBoardViewModel>()

    private var _adapter: TripListAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()

    }

    override fun onDashBoardSelectedListener(tripCreate: TripCreateListModel) {
        // 여행 생성 레이아웃 클릭 시 처리
    }

    private fun setRecycler() {
        _adapter = TripListAdapter(this)
        binding.rvDashboardCompletedTrip.adapter = adapter
        adapter.submitList(viewModel.mockDataList)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}