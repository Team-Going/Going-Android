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
        ),
        TripCreateListModel(
            title ="상호랑 제주도 여행",
            startDate = "2025.03.24",
            endDate = "2025.03.31",
            day = 100
        ),
        TripCreateListModel(
            title ="동민이랑 서울 구경",
            startDate = "2026.03.24",
            endDate = "2026.03.31",
            day = 200
        ),
        TripCreateListModel(
            title ="유빈이랑 부산 여행",
            startDate = "2027.03.24",
            endDate = "2027.03.31",
            day = 300
        ),
        TripCreateListModel(
            title ="세연이랑 바다 구경",
            startDate = "2028.03.24",
            endDate = "2028.03.31",
            day = 400
        )
    )
}