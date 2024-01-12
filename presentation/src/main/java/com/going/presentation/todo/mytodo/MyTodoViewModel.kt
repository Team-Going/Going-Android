package com.going.presentation.todo.mytodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.MyTripInfoModel
import com.going.domain.entity.response.TodoModel
import com.going.domain.repository.TodoRepository
import com.going.presentation.todo.ourtodo.OurTodoViewModel
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

    private val _myTripInfoState = MutableStateFlow<UiState<MyTripInfoModel>>(UiState.Empty)
    val myTripInfoState: StateFlow<UiState<MyTripInfoModel>> = _myTripInfoState

    private val _totalUncompletedTodoCount = MutableStateFlow<Int>(0)
    val totalUncompletedTodoCount: StateFlow<Int> = _totalUncompletedTodoCount

    private val _todoUncompleteListState = MutableStateFlow<UiState<List<TodoModel>>>(UiState.Empty)
    val todoUncompleteListState: StateFlow<UiState<List<TodoModel>>> = _todoUncompleteListState

    private val _todoCompleteListState = MutableStateFlow<UiState<List<TodoModel>>>(UiState.Empty)
    val todoCompleteListState: StateFlow<UiState<List<TodoModel>>> = _todoCompleteListState

    fun decreaseTodoCount() {
        _totalUncompletedTodoCount.value = _totalUncompletedTodoCount.value - 1
    }

    fun getMyTripInfoFromServer(tripId: Long) {
        _myTripInfoState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getMyTripInfo(tripId)
                .onSuccess { response ->
                    _myTripInfoState.value = UiState.Success(response)
                }
                .onFailure {
                    _myTripInfoState.value = UiState.Failure(it.message.toString())
                }
        }
    }

    fun getUncompleteTodoListFromServer(tripId: Long, category: String, progress: String) {
        _todoUncompleteListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    _todoUncompleteListState.value = UiState.Success(response)
                    _totalUncompletedTodoCount.value = response.size
                }
                .onFailure {
                    _todoUncompleteListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun getCompleteTodoListFromServer(tripId: Long, category: String, progress: String) {
        _todoCompleteListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    _todoCompleteListState.value = UiState.Success(response)
                }
                .onFailure {
                    _todoCompleteListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    companion object {
        const val MY_TODO = "my"
        const val UNCOMPLETE = "incomplete"
        const val COMPLETE = "complete"
    }
}