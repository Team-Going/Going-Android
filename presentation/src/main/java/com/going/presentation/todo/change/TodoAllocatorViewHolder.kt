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

            setShapeColor(item.isOwner)
            setTextColor()

            layoutTodoName.setOnClickListener {
                itemClick(position)
                tvTodoName.isSelected = !tvTodoName.isSelected
                setTextColor()
            }
        }
    }

    private fun setShapeColor(isOwner: Boolean) {
        with(binding.tvTodoName) {
            if (isOwner) {
                setBackgroundResource(R.drawable.sel_todo_shape_red500_fill)
            } else {
                setBackgroundResource(R.drawable.sel_todo_shape_gray400_fill)
            }
        }
    }
    private fun setTextColor() {
        with(binding.tvTodoName) {
            if (isSelected) {
                setTextColor(binding.root.context.colorOf(R.color.white_000))
            } else {
                setTextColor(binding.root.context.colorOf(R.color.gray_300))
            }
        }
    }
}