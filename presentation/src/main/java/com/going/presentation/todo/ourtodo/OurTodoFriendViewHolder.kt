package com.going.presentation.todo.ourtodo

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoFriendsBinding

class OurTodoFriendViewHolder(val binding: ItemTodoFriendsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripParticipantModel) {
        binding.run {
            tvTodoFriend.text = item.name

            val profileImage = when (item.result) {
                0 -> R.drawable.img_profile_1
                1 -> R.drawable.img_profile_2
                2 -> R.drawable.img_profile_3
                3 -> R.drawable.img_profile_4
                4 -> R.drawable.img_profile_5
                5 -> R.drawable.img_profile_6
                6 -> R.drawable.img_profile_7
                else -> R.drawable.img_profile_8
            }
            ivTodoFriend.load(profileImage) {
                transformations(CircleCropTransformation())
            }

        }
    }

}