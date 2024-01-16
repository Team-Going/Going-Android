package com.going.presentation.dashboard.triplist.completed

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemDashBoardCompletedBinding
import com.going.ui.extension.setOnSingleClickListener

class CompletedViewHolder(
    val binding: ItemDashBoardCompletedBinding,
    private val listener: CompletedAdapter.OnDashBoardSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: DashBoardTripModel) {

        binding.run {
            tvDashboardTripTitle.text = item.title
            tvDashboardDate.text = String.format(
                itemView.context.getString(R.string.dashboard_tv_start_end_date),
                item.startDate,
                item.endDate
            )
            tvDashboardDeadlineCompleted.text =
                itemView.context.getString(R.string.dashboard_tv_completed_trip)

            layoutDashboard.setOnSingleClickListener {
                listener.onDashBoardSelectedListener(item)
            }
        }
    }
}
