package com.going.presentation.todo.change

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.util.ItemDiffCallback

class TodoAllocatorAdapter(
    private val itemClick: (Int) -> Unit
) : ListAdapter<TodoAllocatorModel, TodoAllocatorViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAllocatorViewHolder {
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoAllocatorViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: TodoAllocatorViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoAllocatorModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}