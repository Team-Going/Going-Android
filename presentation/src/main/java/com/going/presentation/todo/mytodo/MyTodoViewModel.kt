package com.going.presentation.todo.mytodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.domain.entity.response.TodoModel
import com.going.domain.repository.TodoRepository
import com.going.ui.extension.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _totalUncompletedTodoCount = MutableStateFlow<Int>(0)
    val totalUncompletedTodoCount: StateFlow<Int> = _totalUncompletedTodoCount

    private val _todoListState = MutableStateFlow<UiState<List<TodoModel>>>(UiState.Empty)
    val todoListState: StateFlow<UiState<List<TodoModel>>> = _todoListState

    fun setTodoCount() {
        _totalUncompletedTodoCount.value = mockUncompleteTodoList.size
    }

    fun decreaseTodoCount() {
        _totalUncompletedTodoCount.value = _totalUncompletedTodoCount.value - 1
    }

    fun getTodoListFromServer(tripId: Long, category: String, process: String) {
        _todoListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, process)
                .onSuccess { response ->
                    _todoListState.value =UiState.Success(response)
                }
                .onFailure {
                    _todoListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    companion object {
        const val MY_TODO = "my"
        const val OUR_TODO = "our"
        const val UNCOMPLETE = "incomplete"
        const val COMPLETE = "complete"
    }

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