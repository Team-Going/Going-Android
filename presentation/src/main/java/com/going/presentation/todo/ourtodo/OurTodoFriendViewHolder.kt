package com.going.presentation.todo.ourtodo

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoFriendsBinding

class OurTodoFriendViewHolder(val binding: ItemTodoFriendsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripParticipantModel) {
        binding.run {
            tvTodoFriend.text = item.name
            ivTodoFriend.load(R.drawable.ic_todo_friend) {
                transformations(CircleCropTransformation())
            }
        }
    }

}