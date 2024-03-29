package com.going.presentation.todo.mytodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.MyTripInfoModel
import com.going.domain.entity.response.TodoModel
import com.going.domain.repository.TodoRepository
import com.going.ui.state.EnumUiState
import com.going.ui.state.UiState
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

    private val _todoFinishState = MutableStateFlow<EnumUiState>(EnumUiState.EMPTY)
    val todoFinishState: StateFlow<EnumUiState> = _todoFinishState

    private val _todoRedoState = MutableStateFlow<EnumUiState>(EnumUiState.EMPTY)
    val todoRedoState: StateFlow<EnumUiState> = _todoRedoState

    var tripId: Long = 0
    var participantId: Int = 9

    fun increaseTodoCount() {
        _totalUncompletedTodoCount.value += 1
    }

    fun getMyTripInfoFromServer() {
        _myTripInfoState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getMyTripInfo(tripId)
                .onSuccess { response ->
                    participantId = response.participantId.toInt()
                    _myTripInfoState.value = UiState.Success(response)
                }
                .onFailure {
                    _myTripInfoState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun getUncompleteTodoListFromServer(category: String, progress: String) {
        _todoUncompleteListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    _totalUncompletedTodoCount.value = response.size
                    if (response.isEmpty()) {
                        _todoUncompleteListState.value = UiState.Empty
                    } else {
                        _todoUncompleteListState.value = UiState.Success(response)
                    }
                }
                .onFailure {
                    _todoUncompleteListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun getCompleteTodoListFromServer(category: String, progress: String) {
        _todoCompleteListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    if (response.isEmpty()) {
                        _todoCompleteListState.value = UiState.Empty
                    } else {
                        _todoCompleteListState.value = UiState.Success(response)
                    }
                }
                .onFailure {
                    _todoCompleteListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun resetListState() {
        _todoCompleteListState.value = UiState.Empty
        _todoUncompleteListState.value = UiState.Empty
        _todoFinishState.value = EnumUiState.EMPTY
        _todoRedoState.value = EnumUiState.EMPTY
    }

    fun getToFinishTodoFromServer(todoId: Long) {
        _todoFinishState.value = EnumUiState.LOADING
        viewModelScope.launch {
            todoRepository.getToFinishTodo(todoId)
                .onSuccess {
                    _todoFinishState.value = EnumUiState.SUCCESS
                }
                .onFailure {
                    _todoFinishState.value = EnumUiState.FAILURE
                }
        }
    }

    fun getToRedoTodoFromServer(todoId: Long) {
        _todoRedoState.value = EnumUiState.LOADING
        viewModelScope.launch {
            todoRepository.getToRedoTodo(todoId)
                .onSuccess {
                    _todoRedoState.value = EnumUiState.SUCCESS
                }
                .onFailure {
                    _todoRedoState.value = EnumUiState.FAILURE
                }
        }
    }

    companion object {
        const val MY_TODO = "my"
        const val UNCOMPLETE = "incomplete"
        const val COMPLETE = "complete"
    }
}