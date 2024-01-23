package com.going.presentation.todo.ourtodo.create

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.extension.colorOf

class TodoCreateNameViewHolder(
    val binding: ItemTodoCreateNameBinding,
    private val isFixed: Boolean,
    private val itemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val whiteColor = binding.root.context.colorOf(R.color.white_000)
    private val grayColor = binding.root.context.colorOf(R.color.gray_300)

    fun onBind(item: TripParticipantModel, position: Int) {
        binding.run {
            tvTodoName.text = item.name

            if (position == 0) {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_red500_fill)
            } else {
                tvTodoName.setBackgroundResource(R.drawable.sel_todo_shape_gray400_fill)
            }

            if (isFixed) {
                tvTodoName.isSelected = true
                tvTodoName.setTextColor(whiteColor)
            } else {
                layoutTodoName.setOnClickListener {
                    itemClick(position)
                    tvTodoName.isSelected = !tvTodoName.isSelected
                    if (tvTodoName.isSelected) {
                        tvTodoName.setTextColor(whiteColor)
                    } else {
                        tvTodoName.setTextColor(grayColor)
                    }
                }
            }
        }
    }

}