package com.going.presentation.todo.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.presentation.databinding.ItemTodoNameBinding
import com.going.ui.extension.ItemDiffCallback

class TodoNameAdapter(
    private val isCompleted: Boolean
) : ListAdapter<String, TodoNameViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoNameViewHolder {
        val binding: ItemTodoNameBinding =
            ItemTodoNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoNameViewHolder(binding, isCompleted)
    }

    override fun onBindViewHolder(holder: TodoNameViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<String>(
            onItemsTheSame = { old, new -> old.length == new.length },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}