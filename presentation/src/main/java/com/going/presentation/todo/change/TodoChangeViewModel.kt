package com.going.presentation.todo.change

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.TodoChangeRequestModel
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.domain.entity.response.TodoDetailModel
import com.going.domain.repository.TodoRepository
import com.going.presentation.designsystem.edittext.EditTextState
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoChangeViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    private val _todoDetailState = MutableStateFlow<UiState<TodoDetailModel>>(UiState.Empty)
    val todoDetailState: StateFlow<UiState<TodoDetailModel>> = _todoDetailState

    private val _todoPatchState = MutableSharedFlow<Boolean>()
    val todoPatchState: SharedFlow<Boolean> = _todoPatchState

    val todo = MutableLiveData("")
    val memo = MutableLiveData("")
    val endDate = MutableLiveData("")

    private lateinit var oldTodoInfo: TodoDetailModel
    var isSecret: Boolean = false

    private val isTodoAvailable = MutableLiveData(false)
    private val isMemoAvailable = MutableLiveData(true)
    val isFinishAvailable = MutableLiveData(false)

    var allocatorModelList: List<TodoAllocatorModel> = listOf()

    var tripId: Long = 0
    var todoId: Long = 0

    fun setNameState(name: String, state: EditTextState) {
        todo.value = name
        isTodoAvailable.value = state == EditTextState.SUCCESS
        checkIsFinishAvailable()
    }

    fun setMemoState(memoText: String, state: EditTextState) {
        memo.value = memoText
        isMemoAvailable.value = state != EditTextState.OVER
        checkIsFinishAvailable()
    }

    fun checkIsFinishAvailable() {
        isFinishAvailable.value =
            (isTodoAvailable.value == true) && (isMemoAvailable.value == true) && (!endDate.value.isNullOrEmpty()) && checkIsTodoChanged()
    }

    private fun checkIsTodoChanged(): Boolean =
        (todo.value != oldTodoInfo.title) || (endDate.value != oldTodoInfo.endDate) || (memo.value != oldTodoInfo.memo) || checkIsListChanged()

    private fun checkIsListChanged(): Boolean {
        for (i in allocatorModelList.indices) {
            if (oldTodoInfo.allocators[i].isAllocated != allocatorModelList[i].isAllocated) {
                return true
            }
        }
        return false
    }

    fun getTodoDetailFromServer() {
        _todoDetailState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoDetail(tripId, todoId)
                .onSuccess { response ->
                    todo.value = response.title
                    endDate.value = response.endDate
                    allocatorModelList = response.allocators.map { it.copy() }
                    memo.value = response.memo
                    isSecret = response.secret
                    oldTodoInfo = response
                    _todoDetailState.value = UiState.Success(response)
                }
                .onFailure {
                    _todoDetailState.value = UiState.Failure(it.message.toString())
                }
        }
    }

    fun patchTodoToServer() {
        if (isFinishAvailable.value == false) return
        viewModelScope.launch {
            todoRepository.patchTodo(
                tripId,
                todoId,
                TodoChangeRequestModel(
                    title = todo.value.orEmpty(),
                    endDate = endDate.value.orEmpty(),
                    allocators = allocatorModelList.filter { it.isAllocated }.map { it.participantId },
                    memo = memo.value,
                    secret = isSecret
                )
            )
                .onSuccess {
                    _todoPatchState.emit(true)
                }
                .onFailure {
                    _todoPatchState.emit(false)
                }
        }
    }

}