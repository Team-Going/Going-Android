package com.going.presentation.mock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.MockFollowerModel
import com.going.domain.repository.MockRepository
import com.going.ui.extension.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MockViewModel @Inject constructor(
    private val mockRepository: MockRepository
) : ViewModel() {

    private val _followerListState = MutableStateFlow<UiState<List<MockFollowerModel>>>(UiState.Empty)
    val followerListState: StateFlow<UiState<List<MockFollowerModel>>> = _followerListState

    fun getFollowerListFromServer(page: Int) {
        _followerListState.value = UiState.Loading
        viewModelScope.launch {
            mockRepository.getFollowerList(page)
                .onSuccess { response ->
                    _followerListState.value = UiState.Success(response)
                }
                .onFailure {
                    _followerListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }
}