package com.going.presentation.checkfriends

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.going.domain.entity.response.TripParticipantsListModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoFriendsBinding

class CheckFriendsViewHolder(val binding: ItemTodoFriendsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripParticipantsListModel.TripParticipantModel) {
        binding.run {
            tvTodoFriend.text = item.name
            ivTodoFriend.load(R.drawable.ic_todo_friend) {
                transformations(CircleCropTransformation())
            }
        }
    }

}