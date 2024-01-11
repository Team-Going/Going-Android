package com.going.presentation.todo.mytodo.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TodoModel
import com.going.presentation.databinding.ItemMyTodoBinding
import timber.log.Timber

class MyTodoListAdapter(
    private val isCompleted: Boolean,
    private val itemSelect: (Int) -> Unit,
    private val itemUnselect: (Int) -> Unit,
    private val itemDetailClick: (Long) -> Unit
) : RecyclerView.Adapter<MyTodoListViewHolder>() {

    private var itemList = mutableListOf<TodoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTodoListViewHolder {
        val binding: ItemMyTodoBinding =
            ItemMyTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyTodoListViewHolder(binding, isCompleted, itemSelect, itemUnselect, itemDetailClick)
    }

    override fun onBindViewHolder(holder: MyTodoListViewHolder, position: Int) {
        holder.onBind(itemList[position], position)
    }

    override fun getItemCount(): Int = itemList.size

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun setItemList(newItems: List<TodoModel>) {
        this.itemList.addAll(newItems)
        Timber.tag("okhttp").d("@@@${this.itemList}")
        notifyItemRangeInserted(0, itemCount)
    }
}