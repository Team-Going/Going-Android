package com.going.presentation.tripdashboard

import android.widget.ListAdapter
import com.going.domain.entity.response.TripCreateListModel
import com.going.domain.entity.response.TripParticipantsListModel
import com.going.ui.extension.ItemDiffCallback

class TripDashBoardAdapter : ListAdapter<TripCreateListModel, TripDashBoardViewHolder> (diffUtil){



    companion object {
        private val diffUtil = ItemDiffCallback<TripParticipantsListModel.TripParticipantModel>(
            onItemsTheSame = { old, new -> old.participantId == new.participantId },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}