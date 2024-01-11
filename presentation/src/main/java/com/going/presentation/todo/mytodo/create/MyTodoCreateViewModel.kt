package com.going.presentation.todo.mytodo.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.repository.TodoRepository
import com.going.ui.extension.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.BreakIterator
import javax.inject.Inject

@HiltViewModel
class MyTodoCreateViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todo = MutableLiveData("")
    val nowTodoLength = MutableLiveData(0)

    val endDate = MutableLiveData("")

    val memo = MutableLiveData("")
    val nowMemoLength = MutableLiveData(0)

    val isFinishAvailable = MutableLiveData(false)

    private val _todoCreateState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val todoCreateState: StateFlow<UiState<String>> = _todoCreateState

    fun getMaxTodoLen() = MAX_TODO_LEN

    fun getMaxMemoLen() = MAX_MEMO_LEN

    fun checkIsFinishAvailable() {
        nowTodoLength.value = getGraphemeLength(todo.value)
        nowMemoLength.value = getGraphemeLength(memo.value)
        isFinishAvailable.value =
            todo.value?.isNotEmpty() == true && memo.value?.isNotEmpty() == true && endDate.value?.isNotEmpty() == true
    }

    fun postToCreateTodoFromServer(tripId: Long, allocatorId: Long) {
        _todoCreateState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.postToCreateTodo(
                tripId = tripId,
                request = TodoCreateRequestModel(
                    title = todo.value ?: "",
                    endDate = endDate.value ?: "",
                    allocator = listOf(allocatorId),
                    memo = memo.value,
                    secret = true
                )
            )
                .onSuccess { response ->
                    _todoCreateState.value = UiState.Success(response.toString())
                }
                .onFailure {
                    _todoCreateState.value = UiState.Failure(it.message.toString())
                }
        }
    }

    // 이모지 포함 글자 수 세는 함수
    private fun getGraphemeLength(value: String?): Int {
        BREAK_ITERATOR.setText(value)
        var count = 0
        while (BREAK_ITERATOR.next() != BreakIterator.DONE) {
            count++
        }
        return count
    }

    companion object {
        val BREAK_ITERATOR: BreakIterator = BreakIterator.getCharacterInstance()

        const val MAX_TODO_LEN = 15
        const val MAX_MEMO_LEN = 1000
    }

}