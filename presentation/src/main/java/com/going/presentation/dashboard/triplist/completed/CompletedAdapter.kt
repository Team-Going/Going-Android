package com.going.presentation.dashboard.triplist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.databinding.ItemDashBoardCompletedBinding
import com.going.ui.extension.ItemDiffCallback

class CompletedAdapter(
    private val listener: OnDashBoardSelectedListener
) : ListAdapter<DashBoardTripModel, CompletedViewHolder>(diffUtil) {

    interface OnDashBoardSelectedListener {
        fun onDashBoardSelectedListener(tripCreate: DashBoardTripModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        val binding: ItemDashBoardCompletedBinding =
            ItemDashBoardCompletedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CompletedViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CompletedViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<DashBoardTripModel>(
            onItemsTheSame = { old, new -> old.title == new.title },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}