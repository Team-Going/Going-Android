package com.going.presentation.todo.ourtodo.friendlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.databinding.ItemTodoFriendsBinding

class OurTodoFriendAdapter(
    private val onClicked: (Long) -> (Unit)
) : RecyclerView.Adapter<OurTodoFriendViewHolder>() {

    private var itemList = mutableListOf<TripParticipantModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurTodoFriendViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemTodoFriendsBinding =
            ItemTodoFriendsBinding.inflate(inflater, parent, false)
        return OurTodoFriendViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: OurTodoFriendViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun submitList(newItems: List<TripParticipantModel>) {
        this.itemList.clear()
        this.itemList.addAll(newItems)
        notifyDataSetChanged()
    }
}