package com.going.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.DashBoardModel
import com.going.domain.repository.DashBoardRepository
import com.going.ui.extension.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val dashBoardRepository: DashBoardRepository,
) : ViewModel() {
    private val _dashBoardOngoingListState =
        MutableStateFlow<UiState<DashBoardModel>>(UiState.Empty)
    val dashBoardOngoingListState: StateFlow<UiState<DashBoardModel>> get() = _dashBoardOngoingListState

    private val _dashBoardCompletedListState =
        MutableStateFlow<UiState<DashBoardModel>>(UiState.Empty)

    val name = MutableLiveData("")

    val dashBoardCompletedListState: StateFlow<UiState<DashBoardModel>> get() = _dashBoardCompletedListState

    fun getTripListFromServer(
        progress: String,
    ) {
        val dashBoardListState = if (progress == ONGOING) {
            _dashBoardOngoingListState
        } else {
            _dashBoardCompletedListState
        }
        dashBoardListState.value = UiState.Loading
        viewModelScope.launch {
            dashBoardRepository.getDashBoardList(progress)
                .onSuccess {
                    if (it.trips.isEmpty()) {
                        dashBoardListState.value = UiState.Empty
                    } else {
                        dashBoardListState.value = UiState.Success(it)
                    }
                }
                .onFailure {
                    dashBoardListState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun getTravelerNameFromServer(progress: String) {
        viewModelScope.launch {
            dashBoardRepository.getDashBoardList(progress)
                .onSuccess {
                    name.value = it.name
                }
                .onFailure {
                    name.value = null
                }
        }
    }

    companion object {
        const val ONGOING = "incomplete"
        const val COMPLETED = "complete"
    }
}
