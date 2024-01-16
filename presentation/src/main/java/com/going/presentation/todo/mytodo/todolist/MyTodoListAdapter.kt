package com.going.presentation.todo.mytodo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemMyTodoBinding

class MyTodoListAdapter(
    private val isCompleted: Boolean,
    private val itemSelect: (Long) -> Unit,
    private val itemUnselect: (Long) -> Unit,
    private val itemDetailClick: (TodoModel) -> Unit
) : RecyclerView.Adapter<MyTodoListViewHolder>() {

    private var itemList = mutableListOf<TodoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodoListViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemMyTodoBinding = ItemMyTodoBinding.inflate(inflater, parent, false)
        return MyTodoListViewHolder(binding, isCompleted, itemSelect, itemUnselect, itemDetailClick)
    }

    override fun onBindViewHolder(holder: MyTodoListViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun submitList(newItems: List<TodoModel>) {
        this.itemList.clear()
        this.itemList.addAll(newItems)
        notifyDataSetChanged()
    }

}