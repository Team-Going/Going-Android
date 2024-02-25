package com.going.presentation.todo.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.entity.response.TripParticipantModel
import com.going.domain.repository.TodoRepository
import com.going.presentation.designsystem.edittext.EditTextState
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoCreateViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    val todo = MutableLiveData("")
    val endDate = MutableLiveData("")
    val memo = MutableLiveData("")

    private val isTodoAvailable = MutableLiveData(false)
    private val isMemoAvailable = MutableLiveData(false)
    val isFinishAvailable = MutableLiveData(false)

    private val _todoCreateState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val todoCreateState: StateFlow<UiState<Unit>> = _todoCreateState

    var participantModelList: List<TripParticipantModel> = listOf()
    var participantIdList: List<Long> = listOf()

    var tripId: Long = 0

    fun setNameState(name: String, state: EditTextState) {
        todo.value = name
        isTodoAvailable.value = state == EditTextState.SUCCESS
        checkIsFinishAvailable()
    }

    fun setMemoState(memoText: String, state: EditTextState) {
        memo.value = memoText
        isMemoAvailable.value = state == EditTextState.SUCCESS
        checkIsFinishAvailable()
    }

    fun checkIsFinishAvailable() {
        isFinishAvailable.value =
            isTodoAvailable.value == true && isMemoAvailable.value == true && participantModelList.any { it.isSelected }
    }

    fun postToCreateTodoFromServer() {
        _todoCreateState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.postToCreateTodo(
                tripId = tripId,
                request = TodoCreateRequestModel(
                    title = todo.value.orEmpty(),
                    endDate = endDate.value.orEmpty(),
                    allocators = participantIdList,
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
}
