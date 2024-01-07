package com.going.presentation.tripdashboard

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.databinding.ItemDashBoardTripCreateBinding

class TripDashBoardViewHolder(val binding : ItemDashBoardTripCreateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item : TripCreateListModel){
        binding.tvDashboardTripTitle.text = item.title
        binding.tvDashboardDateStart.text = item.startDate
        binding.tvDashboardDateEnd.text = item.endDate

    }
}