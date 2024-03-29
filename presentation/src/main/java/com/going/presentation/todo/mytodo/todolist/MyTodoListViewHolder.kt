package com.going.presentation.todo.mytodo.todolist

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemMyTodoBinding
import com.going.presentation.todo.list.ListAllocatorAdapter
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener

class MyTodoListViewHolder(
    val binding: ItemMyTodoBinding,
    private val isCompleted: Boolean,
    private val itemSelect: (Long) -> Unit,
    private val itemUnselect: (Long) -> Unit,
    private val itemDetailClick: (TodoModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoModel) {
        binding.run {
            tvMyTodoItemTitle.text = item.title
            tvMyTodoItemDate.text = item.endDate.replace("-", ".") + "까지"

            layoutMyTodoLock.isVisible = item.secret
            rvMyTodoName.isVisible = !item.secret

            cbMyTodoSelected.isVisible = isCompleted
            cbMyTodoUnselected.isVisible = !isCompleted

            rvMyTodoName.adapter = ListAllocatorAdapter(isCompleted).apply {
                submitList(item.allocators)
            }

            if (isCompleted) {
                tvMyTodoItemTitle.setTextColor(binding.root.context.colorOf(R.color.gray_300))
                tvMyTodoItemDate.setTextColor(binding.root.context.colorOf(R.color.gray_200))
                layoutMyTodoLock.setBackgroundResource(R.drawable.shape_rect_2_gray300_line)
                ivMyTodoLock.setImageResource(R.drawable.ic_lock_complete)
                tvMyTodoLock.setTextColor(binding.root.context.colorOf(R.color.gray_300))
            } else {
                tvMyTodoItemTitle.setTextColor(binding.root.context.colorOf(R.color.gray_700))
                tvMyTodoItemDate.setTextColor(binding.root.context.colorOf(R.color.gray_300))
                layoutMyTodoLock.setBackgroundResource(R.drawable.shape_rect_2_gray400_line)
                ivMyTodoLock.setImageResource(R.drawable.ic_lock_uncomplete)
                tvMyTodoLock.setTextColor(binding.root.context.colorOf(R.color.gray_400))
            }

            cbMyTodoUnselected.setOnSingleClickListener {
                itemSelect(item.todoId)
            }

            cbMyTodoSelected.setOnSingleClickListener {
                itemUnselect(item.todoId)
            }

            root.setOnSingleClickListener {
                itemDetailClick(item)
            }
        }
    }

}