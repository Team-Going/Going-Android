package com.going.presentation.dashboard.triplist.ongoing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.databinding.ItemDashBoardOngoingBinding
import com.going.ui.util.ItemDiffCallback

class OngoingAdapter(
    private val listener: OnDashBoardSelectedListener
) : ListAdapter<DashBoardTripModel, OngoingViewHolder>(diffUtil) {

    interface OnDashBoardSelectedListener {
        fun onDashBoardSelectedListener(tripCreate: DashBoardTripModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngoingViewHolder {
        val binding: ItemDashBoardOngoingBinding =
            ItemDashBoardOngoingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return OngoingViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: OngoingViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<DashBoardTripModel>(
            onItemsTheSame = { old, new -> old.title == new.title },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}