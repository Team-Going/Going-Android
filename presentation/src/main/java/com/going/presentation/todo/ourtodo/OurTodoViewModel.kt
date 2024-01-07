package com.going.presentation.todo.ourtodo

import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.TripParticipantsListModel.TripParticipantModel

// @HiltViewModel
class OurTodoViewModel : ViewModel() {

    val mockParticipantsList: List<TripParticipantModel> = listOf(
        TripParticipantModel(0, "일지민", 100),
        TripParticipantModel(1, "이지민", 100),
        TripParticipantModel(2, "삼지민", 100),
        TripParticipantModel(3, "사지민", 100),
        TripParticipantModel(4, "오지민", 100),
        TripParticipantModel(5, "육지민", 100),
        TripParticipantModel(6, "칠지민", 100)
    )
}