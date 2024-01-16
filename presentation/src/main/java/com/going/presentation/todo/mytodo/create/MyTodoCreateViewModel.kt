package com.going.presentation.todo.mytodo.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.repository.TodoRepository
import com.going.ui.extension.UiState
import com.going.ui.extension.getGraphemeLength
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTodoCreateViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    val todo = MutableLiveData("")
    val nowTodoLength = MutableLiveData(0)

    val endDate = MutableLiveData("")

    val memo = MutableLiveData("")
    val nowMemoLength = MutableLiveData(0)

    val isFinishAvailable = MutableLiveData(false)

    private val _todoCreateState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val todoCreateState: StateFlow<UiState<Unit>> = _todoCreateState

    var tripId: Long = 0
    var participantId: Long = 0

    fun getMaxTodoLen() = MAX_TODO_LEN

    fun getMaxMemoLen() = MAX_MEMO_LEN

    fun checkIsFinishAvailable() {
        nowTodoLength.value = todo.value?.getGraphemeLength()
        nowMemoLength.value = memo.value?.getGraphemeLength()
        isFinishAvailable.value =
            todo.value?.isNotEmpty() == true && endDate.value?.isNotEmpty() == true
    }

    fun postToCreateTodoFromServer() {
        _todoCreateState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.postToCreateTodo(
                tripId = tripId,
                request = TodoCreateRequestModel(
                    title = todo.value ?: "",
                    endDate = endDate.value ?: "",
                    allocators = listOf(participantId),
                    memo = memo.value,
                    secret = true,
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
