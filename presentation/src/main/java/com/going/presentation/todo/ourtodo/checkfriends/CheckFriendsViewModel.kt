package com.going.presentation.todo.ourtodo.checkfriends

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.CheckFriendsModel
import com.going.domain.repository.TodoRepository
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckFriendsViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _checkFriendsListState = MutableStateFlow<UiState<CheckFriendsModel>>(UiState.Empty)
    val checkFriendsListState: StateFlow<UiState<CheckFriendsModel>> get() = _checkFriendsListState

    fun getFriendsListFromServer(
        tripId: Long
    ) {
        _checkFriendsListState.value = UiState.Loading
        viewModelScope.launch {
            todoRepository.getFriendsList(tripId)
                .onSuccess {
                    Log.e("TAG", "success", )
                    _checkFriendsListState.value = UiState.Success(it)
                }
                .onFailure {
                    Log.e("TAG", "fail", )
                    _checkFriendsListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

}