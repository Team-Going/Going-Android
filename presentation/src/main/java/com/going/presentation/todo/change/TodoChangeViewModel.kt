package com.going.presentation.todo.change

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.TodoAllocatorModel
import com.going.domain.repository.TodoRepository
import com.going.presentation.designsystem.edittext.EditTextState
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoChangeViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    private val _todoDetailState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val todoDetailState: StateFlow<UiState<Boolean>> = _todoDetailState

    private val todo = MutableLiveData("")
    private val memo = MutableLiveData("")

    val endDate = MutableLiveData("")

    private lateinit var oldTitle: String
    private lateinit var oldEndDate: String
    private lateinit var oldAllocatorList : List<Long>
    private lateinit var oldMemo: String
    private var isSecret: Boolean = false

    private val isTodoAvailable = MutableLiveData(false)
    private val isMemoAvailable = MutableLiveData(true)
    val isFinishAvailable = MutableLiveData(false)

    var allocatorModelList: List<TodoAllocatorModel> = listOf()

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
        // TODO 수정 여부 확인
        isFinishAvailable.value =
            isTodoAvailable.value == true && isMemoAvailable.value == true && !endDate.value.isNullOrEmpty()
    }

    fun getTodoDetailFromServer() {
        _todoDetailState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getTodoDetail(todoId)
                .onSuccess { response ->
                    todo.value = response.title
                    endDate.value = response.endDate
                    allocatorModelList = response.allocators
                    memo.value = response.memo
                    isSecret = response.secret
                    oldTitle = response.title
                    oldEndDate = response.endDate
                    oldAllocatorList = response.allocators.map { it.participantId }
                    oldMemo = response.memo
                    _todoDetailState.value = UiState.Success(response.secret)
                }
                .onFailure {
                    _todoDetailState.value = UiState.Failure(it.message.toString())
                }
        }
    }

}