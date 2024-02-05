package com.going.presentation.dashboard.triplist.ongoing

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemDashBoardOngoingBinding
import com.going.ui.extension.setOnSingleClickListener

class OngoingViewHolder(
    val binding: ItemDashBoardOngoingBinding,
    private val itemDetailClick: (DashBoardTripModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: DashBoardTripModel) {
        binding.run {
            tvDashboardTripTitle.text = item.title
            tvDashboardDate.text = String.format(
                itemView.context.getString(R.string.dashboard_tv_start_end_date),
                item.startDate,
                item.endDate
            )

            when {
                item.day <= 0 -> {
                    tvDashboardDeadline.text =
                        itemView.context.getString(R.string.dashboard_tv_traveling)
                }

                else -> {
                    tvDashboardDeadline.text =
                        itemView.context.getString(R.string.dashboard_tv_deadline, item.day)
                }
            }

            root.setOnSingleClickListener {
                itemDetailClick(item)
            }
        }
    }
}
