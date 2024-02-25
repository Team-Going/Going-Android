package com.going.presentation.todo.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.extension.ItemDiffCallback

class TodoCreateNameAdapter(
    private val isFixed: Boolean,
    private val itemClick: (Int) -> Unit
) : ListAdapter<TripParticipantModel, TodoCreateNameViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoCreateNameViewHolder {
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoCreateNameViewHolder(binding, isFixed, itemClick)
    }

    override fun onBindViewHolder(holder: TodoCreateNameViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripParticipantModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}