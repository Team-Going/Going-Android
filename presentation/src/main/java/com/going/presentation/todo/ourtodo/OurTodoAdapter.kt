package com.going.presentation.todo.ourtodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripParticipantsListModel.TripParticipantModel
import com.going.presentation.databinding.ItemTodoFriendsBinding
import com.going.ui.extension.ItemDiffCallback

class OurTodoAdapter : ListAdapter<TripParticipantModel, OurTodoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurTodoViewHolder {
        val binding: ItemTodoFriendsBinding =
            ItemTodoFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurTodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OurTodoViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun addList(newItems: List<TripParticipantModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripParticipantModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}