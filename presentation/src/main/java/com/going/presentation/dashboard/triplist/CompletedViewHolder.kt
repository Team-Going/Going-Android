package com.going.presentation.dashboard.triplist

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.DashBoardModel
import com.going.presentation.databinding.ItemDashBoardCompletedBinding
import com.going.ui.extension.setOnSingleClickListener

class CompletedViewHolder(
    val binding: ItemDashBoardCompletedBinding,
    private val listener: CompletedAdapter.OnDashBoardSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: DashBoardModel.DashBoardTripModel) {

        binding.run {
            tvDashboardTripTitle.text = item.title
            tvDashboardDateStart.text = item.startDate
            tvDashboardDateEnd.text = item.endDate

            layoutDashboard.setOnSingleClickListener {
                listener.onDashBoardSelectedListener(item)
            }
        }
    }
}
