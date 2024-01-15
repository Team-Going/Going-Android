package com.going.presentation.todo.ourtodo.checkfriends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.CheckFriendsModel.ParticipantsModel
import com.going.presentation.databinding.ItemTodoFriendsBinding
import com.going.ui.extension.ItemDiffCallback

class CheckFriendsAdapter : ListAdapter<ParticipantsModel, CheckFriendsViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckFriendsViewHolder {
        val binding: ItemTodoFriendsBinding =
            ItemTodoFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckFriendsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<ParticipantsModel>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}