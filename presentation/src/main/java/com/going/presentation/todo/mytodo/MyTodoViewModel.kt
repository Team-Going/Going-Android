package com.going.presentation.todo.mytodo

import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.TodoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// @HiltViewModel
class MyTodoViewModel : ViewModel() {

    private val _totalUncompletedTodoCount = MutableStateFlow<Int>(0)
    val totalUncompletedTodoCount: StateFlow<Int> = _totalUncompletedTodoCount

    fun setTodoCount() {
        _totalUncompletedTodoCount.value = mockUncompleteTodoList.size
    }

    fun decreaseTodoCount() {
        _totalUncompletedTodoCount.value = _totalUncompletedTodoCount.value - 1
    }

    val mockUncompleteTodoList: List<TodoModel> = listOf(
        TodoModel(0, "숙소 예약하기", "2024-01-12", listOf("김상호", "박동민"), false),
        TodoModel(1, "기차 왕복 예약하기", "2024-01-14", listOf("조세연"), false),
        TodoModel(2, "와사비맛 아몬드 먹기", "2024-01-15", listOf("이유빈", "김상호"), false),
        TodoModel(3, "커피 사기", "2024-01-15", listOf("이유빈"), true),
        TodoModel(4, "숙소 예약하기", "2024-01-12", listOf("김상호", "박동민"), false),
        TodoModel(5, "기차 왕복 예약하기", "2024-01-14", listOf("조세연"), false),
        TodoModel(6, "와사비맛 아몬드 먹기", "2024-01-15", listOf("이유빈", "김상호"), false),
        TodoModel(7, "커피 사기", "2024-01-15", listOf("이유빈"), true)
    )

    val mockCompleteTodoList: List<TodoModel> = listOf(
        TodoModel(0, "숙소 예약하기", "2024-01-12", listOf("김상호", "박동민"), false),
        TodoModel(1, "커피 사기", "2024-01-15", listOf("이유빈"), true),
    )
}