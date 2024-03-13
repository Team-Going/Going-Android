package com.going.presentation.todo.detail

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.extension.colorOf

class DetailAllocatorViewHolder(
    val binding: ItemTodoCreateNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoAllocatorModel) {
        binding.run {
            tvTodoName.text = item.name

            val (backgroundResource, textColor) = when {
                !item.isAllocated -> Pair(R.drawable.shape_rect_2_gray300_line, R.color.gray_300)
                item.isOwner -> Pair(R.drawable.shape_rect_2_red500_fill, R.color.white_000)
                else -> Pair(R.drawable.shape_rect_2_gray400_fill, R.color.white_000)
            }
            tvTodoName.apply {
                setBackgroundResource(backgroundResource)
                setTextColor(binding.root.context.colorOf(textColor))
            }
        }
    }

}