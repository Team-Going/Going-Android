package com.going.presentation.todo.ourtodo.todolist

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemOurTodoBinding
import com.going.presentation.todo.name.TodoNameAdapter

class OurTodoListViewHolder(
    val binding: ItemOurTodoBinding,
    private val isCompleted: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoModel) {
        binding.run {
            tvOurTodoItemTitle.text = item.title
            tvOurTodoItemDate.text = item.endDate
            rvOurTodoName.adapter = TodoNameAdapter(isCompleted).apply {
                submitList(item.allocation)
            }
            tvOurTodoItemDate.setTextColor(ContextCompat.getColor(binding.root.context,
                    if (isCompleted) {
                        R.color.gray_200
                    } else {
                        R.color.gray_300
                    }
                )
            )
        }
    }

}