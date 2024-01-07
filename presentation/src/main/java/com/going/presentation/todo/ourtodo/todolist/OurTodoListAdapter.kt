package com.going.presentation.todo.ourtodo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemOurTodoBinding
import com.going.ui.extension.ItemDiffCallback

class OurTodoListAdapter : ListAdapter<TodoModel, OurTodoListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurTodoListViewHolder {
        val binding: ItemOurTodoBinding =
            ItemOurTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurTodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurTodoListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun addList(newItems: List<TodoModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoModel>(
            onItemsTheSame = { old, new -> old.todoId == new.todoId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}