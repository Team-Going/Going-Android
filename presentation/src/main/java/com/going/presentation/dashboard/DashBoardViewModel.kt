package com.going.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.CompletedListModel
import com.going.domain.entity.response.OngoingListModel

class DashBoardViewModel : ViewModel() {

    val mockOngoingList: List<OngoingListModel> = listOf(
        OngoingListModel(
            title = "굉굉이랑 합숙",
            startDate = "2024.01.01",
            endDate = "2024.01.21",
            day = 16
        ),
        OngoingListModel(
            title = "여행 제목 자리",
            startDate = "2002.09.22",
            endDate = "2005.03.31",
            day = -10
        ),
        OngoingListModel(
            title = "상호랑 제주도 여행",
            startDate = "2025.03.24",
            endDate = "2025.03.31",
            day = 100
        ),
        OngoingListModel(
            title = "동민이랑 서울 구경",
            startDate = "2026.03.24",
            endDate = "2026.03.31",
            day = 200
        ),
        OngoingListModel(
            title = "유빈이랑 부산 여행",
            startDate = "2027.03.24",
            endDate = "2027.03.31",
            day = 300
        ),
        OngoingListModel(
            title = "세연이랑 바다 구경",
            startDate = "2028.03.24",
            endDate = "2028.03.31",
            day = 400
        ),
        OngoingListModel(
            title = "솝트랑 MT",
            startDate = "2021.03.24",
            endDate = "2021.03.31",
            day = 0
        ),
        OngoingListModel(
            title = "굉굉 신년회",
            startDate = "2024.01.02",
            endDate = "2024.01.32",
            day = 0
        ),
        OngoingListModel(
            title = "안드 단체 여행",
            startDate = "2020.03.24",
            endDate = "2021.03.31",
            day = 100
        ),
        OngoingListModel(
            title = "두릅",
            startDate = "2003.03.24",
            endDate = "2004.03.31",
            day = 1000
        )
    )

    val mockCompletedList: List<CompletedListModel> = listOf(
        CompletedListModel(
            title = "유빈이 생일 파티",
            startDate = "2022.09.22",
            endDate = "2022.09.23"
        ),
        CompletedListModel(
            title = "굉굉이랑 만화 카페",
            startDate = "2024.01.13",
            endDate = "2024.01.23",
        ),
        CompletedListModel(
            title = "굉굉이랑 파스타 먹기",
            startDate = "2024.01.09",
            endDate = "2024.01.09",
        ),
        CompletedListModel(
            title = "굉굉이랑 고기 구워 먹기",
            startDate = "2020.04.10",
            endDate = "2020.04.22",
        )
    )
}