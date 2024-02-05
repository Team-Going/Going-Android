package com.going.presentation.dashboard.triplist.ongoing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.databinding.ItemDashBoardOngoingBinding
import com.going.ui.extension.ItemDiffCallback

class OngoingAdapter(
    private val itemDetailClick: (DashBoardTripModel) -> Unit
) : ListAdapter<DashBoardTripModel, OngoingViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngoingViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemDashBoardOngoingBinding =
            ItemDashBoardOngoingBinding.inflate(
                inflater,
                parent,
                false
            )
        return OngoingViewHolder(binding, itemDetailClick)
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