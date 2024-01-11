package com.going.presentation.dashboard.triplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.domain.entity.response.OngoingListModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOngoingTripBinding
import com.going.presentation.dashboard.DashBoardViewModel
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OngoingTripFragment :
    BaseFragment<FragmentOngoingTripBinding>(R.layout.fragment_ongoing_trip),
    OngoingAdapter.OnDashBoardSelectedListener {

    private val viewModel by activityViewModels<DashBoardViewModel>()

    private var _adapter: OngoingAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        initItemDecoration()
        setTripList()

    }

    override fun onDashBoardSelectedListener(tripCreate: OngoingListModel) {
        // 여행 생성 레이아웃 클릭 시 처리
    }

    private fun setRecyclerView() {
        _adapter = OngoingAdapter(this)
        binding.rvDashboardOngoingTrip.adapter = adapter
//        adapter.submitList(viewModel.mockOngoingList)
    }

    private fun initItemDecoration() {
        val itemDeco = DashBoardDecoration(requireContext())
        binding.rvDashboardOngoingTrip.addItemDecoration(itemDeco)
    }

    private fun setTripList(){

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}