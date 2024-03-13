package com.going.presentation.todo.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.databinding.ItemTodoCreateNameBinding
import com.going.ui.util.ItemDiffCallback

class CreateParticipantAdapter(
    private val isFixed: Boolean,
    private val itemClick: (Int) -> Unit
) : ListAdapter<TripParticipantModel, CreateParticipantViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateParticipantViewHolder {
        val binding: ItemTodoCreateNameBinding =
            ItemTodoCreateNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreateParticipantViewHolder(binding, isFixed, itemClick)
    }

    override fun onBindViewHolder(holder: CreateParticipantViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<TripParticipantModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}