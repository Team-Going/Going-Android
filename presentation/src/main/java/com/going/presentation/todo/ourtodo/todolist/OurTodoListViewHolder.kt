package com.going.presentation.todo.ourtodo.todolist

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemOurTodoBinding
import com.going.presentation.todo.name.TodoNameAdapter

class OurTodoListViewHolder(val binding: ItemOurTodoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoModel) {
        binding.run {
            tvOurTodoItemTitle.text = item.title
            tvOurTodoItemDate.text = item.endDate
            rvOurTodoName.adapter = TodoNameAdapter().apply {
                submitList(item.allocation)
            }
        }
    }

}