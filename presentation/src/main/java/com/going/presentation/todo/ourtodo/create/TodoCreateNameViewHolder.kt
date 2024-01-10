package com.going.presentation.todo.ourtodo.create

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoCreateNameBinding

class TodoCreateNameViewHolder(
    val binding: ItemTodoCreateNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val whiteColor = ContextCompat.getColor(binding.root.context, R.color.white_000)
    private val grayColor = ContextCompat.getColor(binding.root.context, R.color.gray_300)

    fun onBind(item: String) {
        binding.run {
            tvTodoName.text = item

            if (item == "김상호") {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_red500_fill)
            } else {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_gray400_fill)
            }

            if (tvTodoName.isSelected) {
                tvTodoName.setTextColor(whiteColor)
            } else {
                tvTodoName.setTextColor(grayColor)
            }
        }
    }

}