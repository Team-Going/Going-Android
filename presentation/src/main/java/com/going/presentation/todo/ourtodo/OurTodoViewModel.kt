package com.going.presentation.todo.ourtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.TodoModel
import com.going.domain.entity.response.TripParticipantsListModel.TripParticipantModel
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

    private val _todoListState = MutableStateFlow<UiState<List<TodoModel>>>(UiState.Empty)
    val todoListState: StateFlow<UiState<List<TodoModel>>> = _todoListState

    fun getTodoListFromServer(tripId: Long, category: String, progress: String) {
        _todoListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoList(tripId, category, progress)
                .onSuccess { response ->
                    _todoListState.value = UiState.Success(response)
                }
                .onFailure {
                    _todoListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    companion object {
        const val OUR_TODO = "our"
        const val UNCOMPLETE = "incomplete"
        const val COMPLETE = "complete"
    }

    val mockParticipantsList: List<TripParticipantModel> = listOf(
        TripParticipantModel(0, "일지민", 100),
        TripParticipantModel(1, "이지민", 100),
        TripParticipantModel(2, "삼지민", 100),
        TripParticipantModel(3, "사지민", 100),
        TripParticipantModel(4, "오지민", 100),
        TripParticipantModel(5, "육지민", 100)
    )
}