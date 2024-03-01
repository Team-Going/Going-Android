package com.going.presentation.todo.change

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.extension.colorOf

class TodoAllocatorViewHolder(
    val binding: ItemTodoCreateNameBinding,
    private val itemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoAllocatorModel, position: Int) {
        binding.run {
            tvTodoName.text = item.name
            tvTodoName.isSelected = item.isAllocated

            if (item.isOwner) {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_red500_fill)
            } else {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_gray400_fill)
            }

            layoutTodoName.setOnClickListener {
                itemClick(position)
                tvTodoName.isSelected = !tvTodoName.isSelected
                if (tvTodoName.isSelected) {
                    tvTodoName.setTextColor(binding.root.context.colorOf(R.color.white_000))
                } else {
                    tvTodoName.setTextColor(binding.root.context.colorOf(R.color.gray_300))
                }
            }
        }
    }
}