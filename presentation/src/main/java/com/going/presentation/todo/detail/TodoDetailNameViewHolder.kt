package com.going.presentation.todo.detail

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoCreateNameBinding

class TodoDetailNameViewHolder(
    val binding: ItemTodoCreateNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoAllocatorModel) {
        binding.run {
            tvTodoName.text = item.name
            tvTodoName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white_000))

            if (item.isOwner) {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_red500_fill)
            } else {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_gray400_fill)
            }
        }
    }

}