package com.going.presentation.dashboard.triplist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.DashBoardModel.DashBoardTripModel
import com.going.presentation.databinding.ItemDashBoardCompletedBinding
import com.going.ui.extension.ItemDiffCallback

class CompletedAdapter(
    private val itemDetailClick: (DashBoardTripModel) -> Unit
) : ListAdapter<DashBoardTripModel, CompletedViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemDashBoardCompletedBinding =
            ItemDashBoardCompletedBinding.inflate(
                inflater,
                parent,
                false
            )
        return CompletedViewHolder(binding, itemDetailClick)
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