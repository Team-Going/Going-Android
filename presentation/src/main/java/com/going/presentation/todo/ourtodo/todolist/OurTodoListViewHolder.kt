package com.going.presentation.todo.ourtodo.todolist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemOurTodoBinding
import com.going.presentation.todo.allocator.TodoAllocatorAdapter
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener

class OurTodoListViewHolder(
    val binding: ItemOurTodoBinding,
    private val isCompleted: Boolean,
    private val itemDetailClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TodoModel) {
        binding.run {
            tvOurTodoItemTitle.text = item.title
            tvOurTodoItemDate.text = item.endDate.replace("-", ".") + "까지"
            if (item.allocators.isEmpty()) {
                rvOurTodoName.visibility = View.INVISIBLE
                layoutOurTodoEmptyAllocator.visibility = View.VISIBLE
            } else {
                rvOurTodoName.visibility = View.VISIBLE
                layoutOurTodoEmptyAllocator.visibility = View.INVISIBLE
                rvOurTodoName.adapter = TodoAllocatorAdapter(isCompleted).apply {
                    submitList(item.allocators)
                }
            }

            if (isCompleted) {
                tvOurTodoItemTitle.setTextColor(binding.root.context.colorOf(R.color.gray_300))
                tvOurTodoItemDate.setTextColor(binding.root.context.colorOf(R.color.gray_200))
            } else {
                tvOurTodoItemTitle.setTextColor(binding.root.context.colorOf(R.color.black_000))
                tvOurTodoItemDate.setTextColor(binding.root.context.colorOf(R.color.gray_300))
            }

            root.setOnSingleClickListener {
                itemDetailClick(item.todoId)
            }
        }
    }

}