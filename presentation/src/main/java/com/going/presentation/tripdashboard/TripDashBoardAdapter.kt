package com.going.presentation.tripdashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripCreateListModel
import com.going.domain.entity.response.TripParticipantsListModel
import com.going.presentation.databinding.ItemDashBoardTripCreateBinding
import com.going.ui.extension.ItemDiffCallback

class TripDashBoardAdapter(
    private val listener: OnDashBoardSelectedListener
) : ListAdapter<TripCreateListModel, TripDashBoardViewHolder>(diffUtil) {

    interface OnDashBoardSelectedListener {
        fun onDashBoardSelectedListener(tripCreate: TripCreateListModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripDashBoardViewHolder {
        val binding: ItemDashBoardTripCreateBinding =
            ItemDashBoardTripCreateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TripDashBoardViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TripDashBoardViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripCreateListModel>(
            onItemsTheSame = { old, new -> old.title == new.title },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}