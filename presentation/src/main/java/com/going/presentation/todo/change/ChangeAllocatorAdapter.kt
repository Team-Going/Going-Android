package com.going.presentation.todo.change

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.util.ItemDiffCallback

class ChangeAllocatorAdapter(
    private val itemClick: (Int) -> Unit
) : ListAdapter<TodoAllocatorModel, ChangeAllocatorViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeAllocatorViewHolder {
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChangeAllocatorViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ChangeAllocatorViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoAllocatorModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}