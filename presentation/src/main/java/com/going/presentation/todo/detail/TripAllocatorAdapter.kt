package com.going.presentation.todo.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.util.ItemDiffCallback

class TripAllocatorAdapter : ListAdapter<TodoAllocatorModel, TripAllocatorViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAllocatorViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(inflater, parent, false)
        return TripAllocatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripAllocatorViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoAllocatorModel>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}