package com.going.presentation.todo.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.extension.ItemDiffCallback

class TodoDetailNameAdapter : ListAdapter<TodoAllocatorModel, TodoDetailNameViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoDetailNameViewHolder {
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoDetailNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoDetailNameViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoAllocatorModel>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}