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
        ),
        TripCreateListModel(
            title ="솝트랑 MT",
            startDate = "2021.03.24",
            endDate = "2021.03.31",
            day = -10
        ),
        TripCreateListModel(
            title ="굉굉 신년회",
            startDate = "2024.01.02",
            endDate = "2024.01.32",
            day = -10
        ),
        TripCreateListModel(
            title ="안드 단체 여행",
            startDate = "2020.03.24",
            endDate = "2021.03.31",
            day = -100
        ),
        TripCreateListModel(
            title ="유빈이 생일 파티",
            startDate = "2022.09.22",
            endDate = "2022.09.23",
            day = -120
        ),
        TripCreateListModel(
            title ="두릅",
            startDate = "2003.03.24",
            endDate = "2004.03.31",
            day = -1000
        )
    )
}