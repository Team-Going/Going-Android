package com.going.presentation.todo.ourtodo.friendlist

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ItemTodoFriendsBinding
import com.going.ui.extension.setOnSingleClickListener

class OurTodoFriendViewHolder(
    val binding: ItemTodoFriendsBinding,
    private val onClicked: (Long) -> (Unit)
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: TripParticipantModel) {
        binding.run {
            tvTodoFriend.text = item.name

            ivTodoFriend.load(
                when (item.result) {
                    0 -> R.drawable.img_profile_6
                    1 -> R.drawable.img_profile_1
                    2 -> R.drawable.img_profile_2
                    3 -> R.drawable.img_profile_4
                    4 -> R.drawable.img_profile_8
                    5 -> R.drawable.img_profile_5
                    6 -> R.drawable.img_profile_7
                    7 -> R.drawable.img_profile_3
                    else -> R.drawable.img_profile_guest
                }
            )

            root.setOnSingleClickListener {
                onClicked(item.participantId)
            }

        }
    }

}