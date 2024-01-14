package com.going.presentation.todo.ourtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.OurTripInfoModel
import com.going.domain.entity.response.TodoModel
import com.going.domain.repository.TodoRepository
import com.going.ui.extension.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OurTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _ourTripInfoState = MutableStateFlow<UiState<OurTripInfoModel>>(UiState.Empty)
    val ourTripInfoState: StateFlow<UiState<OurTripInfoModel>> = _ourTripInfoState

    private val _todoUncompleteListState = MutableStateFlow<UiState<List<TodoModel>>>(UiState.Empty)
    val todoUncompleteListState: StateFlow<UiState<List<TodoModel>>> = _todoUncompleteListState

    private val _todoCompleteListState = MutableStateFlow<UiState<List<TodoModel>>>(UiState.Empty)
    val todoCompleteListState: StateFlow<UiState<List<TodoModel>>> = _todoCompleteListState

    var inviteCode : String = ""
    var tripId : Long = 0

    fun getOurTripInfoFromServer() {
        _ourTripInfoState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getOurTripInfo(tripId)
                .onSuccess { response ->
                    inviteCode = response.code
                    _ourTripInfoState.value = UiState.Success(response)
                }
                .onFailure {
                    _ourTripInfoState.value = UiState.Failure(it.message.toString())
                }
        }
    }

    fun getTodoListFromServer(category: String, progress: String) {
        val todoListState = if (progress == COMPLETE) {
            _todoCompleteListState
        } else {
            _todoUncompleteListState
        }
        todoListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    todoListState.value = UiState.Success(response)
                }
                .onFailure {
                    todoListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    companion object {
        const val OUR_TODO = "our"
        const val UNCOMPLETE = "incomplete"
        const val COMPLETE = "complete"
    }
}