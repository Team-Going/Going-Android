package com.going.presentation.todo.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.databinding.ItemTodoNameBinding
import com.going.ui.extension.ItemDiffCallback

class TodoNameAdapter(
    private val isCompleted: Boolean
) : ListAdapter<TodoAllocatorModel, TodoNameViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoNameViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemTodoNameBinding =
            ItemTodoNameBinding.inflate(inflater, parent, false)
        return TodoNameViewHolder(binding, isCompleted)
    }

    override fun onBindViewHolder(holder: TodoNameViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoAllocatorModel>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}