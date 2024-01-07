package com.going.presentation.todo.name

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoNameBinding

class TodoNameViewHolder(
    val binding: ItemTodoNameBinding, private val isCompleted: Boolean
) : RecyclerView.ViewHolder(binding.root) {

    private val redColor = ContextCompat.getColor(binding.root.context, R.color.red_500)
    private val grayColor = ContextCompat.getColor(binding.root.context, R.color.gray_400)
    private val completedColor = ContextCompat.getColor(binding.root.context, R.color.gray_300)

    fun onBind(item: String) {
        binding.run {
            tvTodoName.text = item
            if (isCompleted) {
                tvTodoName.setTextColor(completedColor)
                tvTodoName.setBackgroundResource(R.drawable.shape_rect_2_gray300_line)
            } else {
                if (item == "김상호") {
                    tvTodoName.setTextColor(redColor)
                    tvTodoName.isSelected = true
                } else {
                    tvTodoName.setTextColor(grayColor)
                }
            }
        }
    }

}