package com.going.presentation.checkfriends

import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.TripParticipantsListModel

class CheckFriendsViewModel : ViewModel() {

    val mockParticipantsList: List<TripParticipantsListModel.TripParticipantModel> = listOf(
        TripParticipantsListModel.TripParticipantModel(0, "일지민", 100),
        TripParticipantsListModel.TripParticipantModel(1, "이지민", 100),
        TripParticipantsListModel.TripParticipantModel(2, "삼지민", 100),
        TripParticipantsListModel.TripParticipantModel(3, "사지민", 100),
        TripParticipantsListModel.TripParticipantModel(4, "오지민", 100),
        TripParticipantsListModel.TripParticipantModel(5, "육지민", 100),
    )

}