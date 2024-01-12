package com.going.presentation.todo.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.repository.TodoRepository
import com.going.ui.extension.EnumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivateDetailViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todo = MutableLiveData("")
    val nowTodoLength = MutableLiveData(0)

    val endDate = MutableLiveData("")

    val memo = MutableLiveData("")
    val nowMemoLength = MutableLiveData(0)

    private val _todoDetailState = MutableStateFlow<EnumUiState>(EnumUiState.EMPTY)
    val todoDetailState: StateFlow<EnumUiState> = _todoDetailState

    private val _todoDeleteState = MutableStateFlow<EnumUiState>(EnumUiState.EMPTY)
    val todoDeleteState: StateFlow<EnumUiState> = _todoDeleteState

    fun getTodoDetailFromServer(todoId: Long) {
        _todoDetailState.value = EnumUiState.LOADING
        viewModelScope.launch {
            todoRepository.getTodoDetail(todoId)
                .onSuccess { response ->
                    _todoDetailState.value = EnumUiState.SUCCESS
                    todo.value = response.title
                    endDate.value = response.endDate
                    memo.value = response.memo
                    nowTodoLength.value = response.title.length
                    nowMemoLength.value = response.memo.length
                }
                .onFailure {
                    _todoDetailState.value = EnumUiState.FAILURE
                }
        }
    }

    fun deleteTodoFromServer(todoId: Long) {
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

    companion object {
        const val MAX_TODO_LEN = 15
        const val MAX_MEMO_LEN = 1000
    }

}