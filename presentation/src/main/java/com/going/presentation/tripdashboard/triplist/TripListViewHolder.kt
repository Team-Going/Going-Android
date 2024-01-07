package com.going.presentation.tripdashboard.triplist

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.databinding.ItemDashBoardTripCreateBinding
import com.going.ui.extension.setOnSingleClickListener

class TripListViewHolder(
    val binding: ItemDashBoardTripCreateBinding,
    private val listener: TripListAdapter.OnDashBoardSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripCreateListModel) {
        binding.run{
            tvDashboardTripTitle.text = item.title
            tvDashboardDateStart.text = item.startDate
            tvDashboardDateEnd.text = item.endDate
            tvDashboardDateEnd.text = item.day.toString()

            layoutDashboard.setOnSingleClickListener {
                listener.onDashBoardSelectedListener(item)
            }
        }
    }
}