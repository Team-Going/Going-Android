package com.going.presentation.todo.mytodo.todolist

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemMyTodoBinding
import com.going.presentation.todo.name.TodoNameAdapter

class MyTodoListViewHolder(
    val binding: ItemMyTodoBinding,
    private val isCompleted: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoModel) {
        binding.run {
            tvMyTodoItemTitle.text = item.title
            tvMyTodoItemDate.text = item.endDate

            layoutMyTodoLock.isVisible = item.secret
            rvMyTodoName.isVisible = !item.secret

            rvMyTodoName.adapter = TodoNameAdapter(isCompleted).apply {
                submitList(item.allocation)
            }

            if (isCompleted) {
                tvMyTodoItemTitle.setTextColor(ContextCompat.getColor(binding.root.context,R.color.gray_300))
                tvMyTodoItemDate.setTextColor(ContextCompat.getColor(binding.root.context,R.color.gray_200))
                layoutMyTodoLock.setBackgroundResource(R.drawable.shape_rect_2_gray300_line)
            } else {
                tvMyTodoItemTitle.setTextColor(ContextCompat.getColor(binding.root.context,R.color.black_000))
                tvMyTodoItemDate.setTextColor(ContextCompat.getColor(binding.root.context,R.color.gray_300))
                layoutMyTodoLock.setBackgroundResource(R.drawable.shape_rect_2_gray400_line)
            }

            cbMyTodoSelected.isVisible = isCompleted
            cbMyTodoUnselected.isVisible = !isCompleted
        }
    }

}