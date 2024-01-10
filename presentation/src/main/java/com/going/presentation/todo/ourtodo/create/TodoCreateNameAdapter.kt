package com.going.presentation.todo.ourtodo.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.presentation.databinding.ItemTodoNameBinding
import com.going.ui.extension.ItemDiffCallback

class TodoCreateNameAdapter : ListAdapter<String, TodoCreateNameViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoCreateNameViewHolder {
        val binding: ItemTodoNameBinding =
            ItemTodoNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoCreateNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoCreateNameViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<String>(
            onItemsTheSame = { old, new -> old.length == new.length },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}