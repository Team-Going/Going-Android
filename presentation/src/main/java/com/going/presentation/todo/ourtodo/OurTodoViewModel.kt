package com.going.presentation.todo.ourtodo

import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.domain.entity.response.TodoModel
import com.going.domain.entity.response.TripParticipantsListModel.TripParticipantModel

// @HiltViewModel
class OurTodoViewModel : ViewModel() {

    val mockParticipantsList: List<TripParticipantModel> = listOf(
        TripParticipantModel(0, "일지민", 100),
        TripParticipantModel(1, "이지민", 100),
        TripParticipantModel(2, "삼지민", 100),
        TripParticipantModel(3, "사지민", 100),
        TripParticipantModel(4, "오지민", 100),
        TripParticipantModel(5, "육지민", 100)
    )

    val mockUncompleteTodoList: List<TodoModel> = listOf(
        TodoModel(
            0,
            "숙소 예약하기",
            "2024-01-12",
            listOf(TodoAllocatorModel("김상호", true), TodoAllocatorModel("박동민", false)),
            false
        ),
        TodoModel(
            1,
            "기차 왕복 예약하기",
            "2024-01-14",
            listOf(TodoAllocatorModel("이유빈", false)),
            false
        ),
        TodoModel(
            2,
            "와사비맛 아몬드 먹기",
            "2024-01-15",
            listOf(TodoAllocatorModel("조세연", false)),
            false
        ),
        TodoModel(
            3,
            "커피 사기",
            "2024-01-15",
            listOf(
                TodoAllocatorModel("김상호", true),
                TodoAllocatorModel("박동민", false),
                TodoAllocatorModel("이유빈", false)
            ),
            false
        ),
        TodoModel(
            4,
            "숙소 예약하기",
            "2024-01-12",
            listOf(TodoAllocatorModel("박동민", false)),
            false
        ),
        TodoModel(
            5,
            "기차 왕복 예약하기",
            "2024-01-14",
            listOf(TodoAllocatorModel("김상호", true)),
            false
        ),
        TodoModel(
            6,
            "와사비맛 아몬드 먹기",
            "2024-01-15",
            listOf(TodoAllocatorModel("이유빈", false)),
            false
        ),
        TodoModel(
            7,
            "커피 사기",
            "2024-01-15",
            listOf(TodoAllocatorModel("조세연", false), TodoAllocatorModel("박동민", false)),
            false
        )
    )

    val mockCompleteTodoList: List<TodoModel> = listOf(
        TodoModel(
            0,
            "숙소 예약하기",
            "2024-01-12",
            listOf(TodoAllocatorModel("김상호", true), TodoAllocatorModel("박동민", false)),
            false
        ),
    )
}