package com.going.presentation.tripdashboard.triplist

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemDashBoardTripCreateBinding
import com.going.ui.extension.setOnSingleClickListener

class TripListViewHolder(
    val binding: ItemDashBoardTripCreateBinding,
    private val listener: TripListAdapter.OnDashBoardSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripCreateListModel) {

        var isOnProgress = true
        if (item.day < 0) {
            isOnProgress = false
        }

        binding.run {
            tvDashboardDateStart.text = item.startDate
            tvDashboardDateEnd.text = item.endDate

            if (isOnProgress) {
                tvDashboardDeadlineDay.text = item.day.toString()
                tvDashboardTripTitle.text = item.title
            } else {
                layoutDashboardDayInprogress.visibility = View.INVISIBLE
                layoutDashboardDayCompleted.visibility = View.VISIBLE
                tvDashboardTripTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_300))
            }
            layoutDashboard.setOnSingleClickListener {
                listener.onDashBoardSelectedListener(item)
            }
        }
    }
}
