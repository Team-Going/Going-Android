package com.going.presentation.todo.ourtodo.checkfriends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.databinding.ItemTodoFriendsBinding
import com.going.ui.util.ItemDiffCallback

class CheckFriendsAdapter(
    private val onClicked: (Long) -> Unit
) : ListAdapter<TripParticipantModel, CheckFriendsViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckFriendsViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemTodoFriendsBinding =
            ItemTodoFriendsBinding.inflate(inflater, parent, false)
        return CheckFriendsViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: CheckFriendsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripParticipantModel>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}