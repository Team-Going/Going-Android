package com.going.presentation.todo.mytodo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemMyTodoBinding
import com.going.ui.extension.ItemDiffCallback

class MyTodoListAdapter(
    private val isCompleted: Boolean,
    private val itemSelect: (Int) -> Unit,
    private val itemUnselect: (Int) -> Unit,
    private val itemDetailClick: (TodoModel) -> Unit
) : ListAdapter<TodoModel, MyTodoListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodoListViewHolder {
        val binding: ItemMyTodoBinding =
            ItemMyTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyTodoListViewHolder(binding, isCompleted, itemSelect, itemUnselect, itemDetailClick)
    }

    override fun onBindViewHolder(holder: MyTodoListViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    fun removeItem(position: Int) {
        // itemList.removeAt(position)
        notifyDataSetChanged()
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TodoModel>(
            onItemsTheSame = { old, new -> old.todoId == new.todoId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}