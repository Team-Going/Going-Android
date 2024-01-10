package com.going.presentation.todo.ourtodo.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.extension.ItemDiffCallback

class TodoCreateNameAdapter : ListAdapter<String, TodoCreateNameViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoCreateNameViewHolder {
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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