package com.going.presentation.tripdashboard.triplist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.OngoingListModel
import com.going.presentation.databinding.ItemDashBoardOngoingBinding
import com.going.ui.extension.setOnSingleClickListener

class OngoingViewHolder(
    val binding: ItemDashBoardOngoingBinding,
    private val listener: OngoingAdapter.OnDashBoardSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: OngoingListModel) {

        binding.run {
            tvDashboardTripTitle.text = item.title
            tvDashboardDateStart.text = item.startDate
            tvDashboardDateEnd.text = item.endDate

            if (item.day <= 0) {
                layoutDashboardTraveling.visibility = View.VISIBLE
                layoutDashboardDayLeft.visibility = View.INVISIBLE
            } else {
                tvDashboardDeadlineDay.text = item.day.toString()
            }

            layoutDashboard.setOnSingleClickListener {
                listener.onDashBoardSelectedListener(item)
            }
        }
    }
}
