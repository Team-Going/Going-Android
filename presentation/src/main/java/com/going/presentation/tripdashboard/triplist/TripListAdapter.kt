package com.going.presentation.tripdashboard.triplist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripCreateListModel
import com.going.presentation.databinding.ItemDashBoardTripCreateBinding
import com.going.ui.extension.ItemDiffCallback

class TripListAdapter(
    private val listener: OnDashBoardSelectedListener) : ListAdapter<TripCreateListModel, TripListViewHolder>(diffUtil) {

    interface OnDashBoardSelectedListener {
        fun onDashBoardSelectedListener(tripCreate: TripCreateListModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val binding: ItemDashBoardTripCreateBinding =
            ItemDashBoardTripCreateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TripListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripCreateListModel>(
            onItemsTheSame = { old, new -> old.title == new.title },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}