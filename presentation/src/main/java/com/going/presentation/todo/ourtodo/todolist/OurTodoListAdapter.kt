package com.going.presentation.todo.ourtodo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemOurTodoBinding

class OurTodoListAdapter(
    private val isCompleted: Boolean, private val itemDetailClick: (Long) -> Unit
) : RecyclerView.Adapter<OurTodoListViewHolder>() {

    private var itemList = mutableListOf<TodoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurTodoListViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemOurTodoBinding = ItemOurTodoBinding.inflate(inflater, parent, false)
        return OurTodoListViewHolder(binding, isCompleted, itemDetailClick)
    }

    override fun onBindViewHolder(holder: OurTodoListViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun submitList(newItems: List<TodoModel>) {
        this.itemList.clear()
        this.itemList.addAll(newItems)
        notifyDataSetChanged()
    }

}