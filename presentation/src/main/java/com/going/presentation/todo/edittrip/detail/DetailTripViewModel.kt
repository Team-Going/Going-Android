package com.going.presentation.todo.edittrip.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.TripInfoModel
import com.going.domain.repository.EditTripRepository
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTripViewModel @Inject constructor(
    private val editTripRepository: EditTripRepository
) : ViewModel() {

    val startYear = MutableLiveData<Int>()
    val startMonth = MutableLiveData<Int>()
    val startDay = MutableLiveData<Int>()

    val endYear = MutableLiveData<Int>()
    val endMonth = MutableLiveData<Int>()
    val endDay = MutableLiveData<Int>()

    var tripId: Long = 0
    var title: String = ""
    var startDate: String = ""
    var endDate: String = ""


    private val _tripInfoState = MutableStateFlow<UiState<TripInfoModel>>(UiState.Empty)
    val tripInfoState: StateFlow<UiState<TripInfoModel>> get() = _tripInfoState

    private val _quittripState = MutableSharedFlow<Boolean>()
    val quittripState: SharedFlow<Boolean> = _quittripState

    fun getTripInfoFromServer(tripId: Long) {
        _tripInfoState.value = UiState.Loading
        viewModelScope.launch {
            editTripRepository.getTripInfo(tripId)
                .onSuccess {
                    title = it.title
                    startDate = it.startDate
                    endDate = it.endDate
                    _tripInfoState.value = UiState.Success(it)
                }
                .onFailure {
                    _tripInfoState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }

    fun patchQuitTripFromServer() {
        viewModelScope.launch {
            editTripRepository.patchQuitTrip(
                tripId
            )
                .onSuccess {
                    _quittripState.emit(true)
                }
                .onFailure {
                    _quittripState.emit(false)
                }
        }
    }
}
