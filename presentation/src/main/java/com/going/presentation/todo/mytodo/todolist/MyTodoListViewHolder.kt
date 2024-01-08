package com.going.presentation.todo.mytodo.todolist

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemMyTodoBinding
import com.going.presentation.todo.name.TodoNameAdapter
import com.going.ui.extension.setOnSingleClickListener

class MyTodoListViewHolder(
    val binding: ItemMyTodoBinding,
    private val isCompleted: Boolean,
    private val itemSelect: (Int) -> Unit,
    private val itemUnselect: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoModel,  position: Int) {
        binding.run {
            tvMyTodoItemTitle.text = item.title
            tvMyTodoItemDate.text =  item.endDate.replace("-", ".") + "까지"

            layoutMyTodoLock.isVisible = item.secret
            rvMyTodoName.isVisible = !item.secret

            cbMyTodoSelected.isVisible = isCompleted
            cbMyTodoUnselected.isVisible = !isCompleted

            rvMyTodoName.adapter = TodoNameAdapter(isCompleted).apply {
                submitList(item.allocation)
            }

            if (isCompleted) {
                tvMyTodoItemTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_300))
                tvMyTodoItemDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_200))
                layoutMyTodoLock.setBackgroundResource(R.drawable.shape_rect_2_gray300_line)
                ivMyTodoLock.setImageResource(R.drawable.ic_lock_complete)
                tvMyTodoLock.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_300))
            } else {
                tvMyTodoItemTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black_000))
                tvMyTodoItemDate.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_300))
                layoutMyTodoLock.setBackgroundResource(R.drawable.shape_rect_2_gray400_line)
                ivMyTodoLock.setImageResource(R.drawable.ic_lock_uncomplete)
                tvMyTodoLock.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray_400))
            }

            binding.cbMyTodoUnselected.setOnSingleClickListener {
                itemSelect(position)
            }

            binding.cbMyTodoSelected.setOnSingleClickListener {
                itemUnselect(position)
            }
        }
    }

}