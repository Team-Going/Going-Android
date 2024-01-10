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

    fun decreaseTodoCount() {
        _totalUncompletedTodoCount.value = _totalUncompletedTodoCount.value - 1
    }

    fun getTodoListFromServer(tripId: Long, category: String, progress: String) {
        _todoListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    _todoListState.value =UiState.Success(response)
                    _totalUncompletedTodoCount.value = response.size
                }
                .onFailure {
                    _todoListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    companion object {
        const val MY_TODO = "my"
        const val UNCOMPLETE = "incomplete"
        const val COMPLETE = "complete"
    }
}