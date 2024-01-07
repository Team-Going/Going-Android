package com.going.presentation.tripdashboard

import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.TripCreateListModel

class TripDashBoardViewModel : ViewModel() {

    val mockDataList : List<TripCreateListModel> = listOf(
        TripCreateListModel(
            title = "굉굉이랑 합숙",
            startDate = "2024.03.24",
            endDate = "2024.03.31",
            day = 16
        ),
        TripCreateListModel(
            title ="여행 제목 자리",
            startDate = "2024.03.24",
            endDate = "2024.03.31",
            day = 16
        )
    )
}