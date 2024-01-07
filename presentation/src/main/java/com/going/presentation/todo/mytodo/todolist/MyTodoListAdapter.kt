package com.going.presentation.todo.mytodo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemMyTodoBinding
import com.going.ui.extension.ItemDiffCallback

class MyTodoListAdapter(
    private val isCompleted: Boolean
) : ListAdapter<TodoModel, MyTodoListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodoListViewHolder {
        val binding: ItemMyTodoBinding =
            ItemMyTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyTodoListViewHolder(binding, isCompleted)
    }

    override fun onBindViewHolder(holder: MyTodoListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoModel>(
            onItemsTheSame = { old, new -> old.todoId == new.todoId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}