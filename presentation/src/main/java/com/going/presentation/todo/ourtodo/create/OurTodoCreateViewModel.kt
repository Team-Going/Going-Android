package com.going.presentation.todo.ourtodo.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.NameState
import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.entity.response.TripParticipantModel
import com.going.domain.repository.TodoRepository
import com.going.ui.extension.UiState
import com.going.ui.extension.getGraphemeLength
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OurTodoCreateViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    val todo = MutableLiveData("")
    val isTodoAvailable = MutableLiveData(NameState.Empty)
    val nowTodoLength = MutableLiveData(0)

    val endDate = MutableLiveData("")

    val memo = MutableLiveData("")
    val isMemoAvailable = MutableLiveData(NameState.Empty)
    val nowMemoLength = MutableLiveData(0)

    val isFinishAvailable = MutableLiveData(false)

    private val _todoCreateState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val todoCreateState: StateFlow<UiState<Unit>> = _todoCreateState

    var totalParticipantList: List<TripParticipantModel> = listOf()
    var participantList: List<TripParticipantModel> = listOf()

    var tripId: Long = 0

    fun checkIsFinishAvailable() {
        nowTodoLength.value = todo.value?.getGraphemeLength()
        isTodoAvailable.value = when {
            nowTodoLength.value == 0 -> NameState.Empty
            (nowTodoLength.value ?: 0) > MAX_TODO_LEN -> NameState.OVER
            todo.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }

        nowMemoLength.value = memo.value?.getGraphemeLength()
        isMemoAvailable.value = when {
            nowMemoLength.value == 0 -> NameState.Empty
            (nowMemoLength.value ?: 0) > MAX_MEMO_LEN -> NameState.OVER
            else -> NameState.Success
        }

        isFinishAvailable.value =
            todo.value?.isNotEmpty() == true && endDate.value?.isNotEmpty() == true && participantList.any { it.isSelected } && isTodoAvailable.value == NameState.Success && isMemoAvailable.value != NameState.OVER
    }

    fun postToCreateTodoFromServer() {
        _todoCreateState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.postToCreateTodo(
                tripId = tripId,
                request = TodoCreateRequestModel(
                    title = todo.value.orEmpty(),
                    endDate = endDate.value.orEmpty(),
                    allocators = participantList.map { it.participantId },
                    memo = memo.value,
                    secret = false,
                ),
            )
                .onSuccess { response ->
                    _todoCreateState.value = UiState.Success(response)
                }
                .onFailure {
                    _todoCreateState.value = UiState.Failure(it.message.toString())
                }
        }
    }

    companion object {
        const val MAX_TODO_LEN = 15
        const val MAX_MEMO_LEN = 1000
    }
}
