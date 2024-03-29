package com.going.presentation.todo.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.TodoDetailModel
import com.going.domain.repository.TodoRepository
import com.going.ui.state.EnumUiState
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todo = MutableLiveData("")
    val endDate = MutableLiveData("")
    val memo = MutableLiveData("")

    private val _todoDetailState = MutableStateFlow<UiState<TodoDetailModel>>(UiState.Empty)
    val todoDetailState: StateFlow<UiState<TodoDetailModel>> = _todoDetailState

    private val _todoDeleteState = MutableStateFlow<EnumUiState>(EnumUiState.EMPTY)
    val todoDeleteState: StateFlow<EnumUiState> = _todoDeleteState

    var tripId: Long = 0
    var todoId: Long = 0
    var isPublic: Boolean = true

    fun getTodoDetailFromServer() {
        _todoDetailState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoDetail(tripId, todoId)
                .onSuccess { response ->
                    todo.value = response.title
                    endDate.value = response.endDate
                    memo.value = response.memo
                    _todoDetailState.value = UiState.Success(response)
                }
                .onFailure {
                    _todoDetailState.value = UiState.Failure(it.message.toString())
                }
        }
    }

    fun deleteTodoFromServer() {
        _todoDeleteState.value = EnumUiState.LOADING
        viewModelScope.launch {
            todoRepository.deleteTodo(todoId)
                .onSuccess {
                    _todoDeleteState.value = EnumUiState.SUCCESS
                }
                .onFailure {
                    _todoDeleteState.value = EnumUiState.FAILURE
                }
        }
    }
}