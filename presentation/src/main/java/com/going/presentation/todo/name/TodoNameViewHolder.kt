package com.going.presentation.todo.name

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoNameBinding

class TodoNameViewHolder(val binding: ItemTodoNameBinding) : RecyclerView.ViewHolder(binding.root) {

    private val redColor = ContextCompat.getColor(binding.root.context, R.color.red_500)
    private val grayColor = ContextCompat.getColor(binding.root.context, R.color.gray_400)

    fun onBind(item: String) {
        binding.run {
            tvTodoName.text = item
            tvTodoName.setTextColor(if (item == "김상호") redColor else grayColor)
            if (item == "김상호") layoutTodoName.isSelected = true
        }
    }

}