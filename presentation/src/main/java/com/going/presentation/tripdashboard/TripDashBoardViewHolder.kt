package com.going.presentation.tripdashboard

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.databinding.ItemDashBoardTripCreateBinding
import com.going.ui.extension.setOnSingleClickListener

class TripDashBoardViewHolder(
    val binding: ItemDashBoardTripCreateBinding,
    private val listener: TripDashBoardAdapter.OnDashBoardSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripCreateListModel) {
        binding.tvDashboardTripTitle.text = item.title
        binding.tvDashboardDateStart.text = item.startDate
        binding.tvDashboardDateEnd.text = item.endDate
        binding.tvDashboardDateEnd.text = item.day.toString()

        binding.layoutDashboard.setOnSingleClickListener {
            listener.onDashBoardSelectedListener(item)
        }
    }
}