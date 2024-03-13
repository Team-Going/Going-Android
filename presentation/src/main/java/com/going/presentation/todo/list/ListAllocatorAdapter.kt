package com.going.presentation.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoListAllocatorModel
import com.going.presentation.databinding.ItemTodoNameBinding
import com.going.ui.util.ItemDiffCallback

class ListAllocatorAdapter(
    private val isCompleted: Boolean
) : ListAdapter<TodoListAllocatorModel, ListAllocatorViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAllocatorViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemTodoNameBinding =
            ItemTodoNameBinding.inflate(inflater, parent, false)
        return ListAllocatorViewHolder(binding, isCompleted)
    }

    override fun onBindViewHolder(holder: ListAllocatorViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoListAllocatorModel>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}