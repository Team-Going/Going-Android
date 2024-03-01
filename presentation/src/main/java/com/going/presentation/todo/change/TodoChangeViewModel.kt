package com.going.presentation.todo.change

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.response.TripParticipantModel
import com.going.domain.repository.TodoRepository
import com.going.presentation.designsystem.edittext.EditTextState
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TodoChangeViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    private val todo = MutableLiveData("")
    private val memo = MutableLiveData("")

    val endDate = MutableLiveData("")

    private val isTodoAvailable = MutableLiveData(false)
    private val isMemoAvailable = MutableLiveData(true)
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
        isMemoAvailable.value = state != EditTextState.OVER
        checkIsFinishAvailable()
    }

    fun checkIsFinishAvailable() {
        isFinishAvailable.value =
            isTodoAvailable.value == true && isMemoAvailable.value == true && !endDate.value.isNullOrEmpty()
    }

}