package com.going.presentation.todo.ourtodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripParticipantsListModel.TripParticipantModel
import com.going.presentation.databinding.ItemTodoFriendsBinding
import com.going.ui.extension.ItemDiffCallback

class OurTodoFriendAdapter : ListAdapter<TripParticipantModel, OurTodoFriendViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurTodoFriendViewHolder {
        val binding: ItemTodoFriendsBinding =
            ItemTodoFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurTodoFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurTodoFriendViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripParticipantModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}