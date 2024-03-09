package com.going.presentation.todo.edittrip.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.response.TripInfoModel
import com.going.domain.repository.EditTripRepository
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTripViewModel @Inject constructor(
    private val editTripRepository: EditTripRepository
) : ViewModel() {
    val name = MutableLiveData<String>()
    val nameLength = MutableLiveData(0)

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


    fun getTripInfoFromServer(tripId: Long) {
        _tripInfoState.value = UiState.Loading
        viewModelScope.launch {
            editTripRepository.getTripInfo(tripId)
                .onSuccess {
                    //tripId = it.tripId
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

    companion object {
        const val MAX_TRIP_LEN = 15
    }

}
