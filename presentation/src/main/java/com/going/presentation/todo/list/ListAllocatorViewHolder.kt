package com.going.presentation.todo.list

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoListAllocatorModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoNameBinding
import com.going.ui.extension.colorOf

class ListAllocatorViewHolder(
    val binding: ItemTodoNameBinding,
    private val isCompleted: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoListAllocatorModel) {
        with(binding.tvTodoName) {
            text = item.name
            when {
                isCompleted -> {
                    setTextColor(binding.root.context.colorOf(R.color.gray_300))
                    setBackgroundResource(R.drawable.shape_rect_2_gray300_line)
                }

                item.isOwner -> {
                    setTextColor(binding.root.context.colorOf(R.color.red_500))
                    isSelected = true
                }

                else -> {
                    setTextColor(binding.root.context.colorOf(R.color.gray_400))
                }
            }
        }
    }

}