package com.going.presentation.todo.edittrip.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.EditTripRequestModel
import com.going.domain.repository.EditTripRepository
import com.going.presentation.designsystem.edittext.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTripInfoViewModel @Inject constructor(
    private val editTripRepository: EditTripRepository,
) : ViewModel() {

    private val _tripEditState = MutableSharedFlow<Boolean>()
    val tripEditState: SharedFlow<Boolean> = _tripEditState

    var tripId: Long = 0

    var currentTitle: String = ""
    var currentStartDate: String = ""
    var currentEndDate: String = ""

    val title = MutableLiveData<String>()

    var currentStartYear = MutableLiveData<Int>()
    var currentStartMonth = MutableLiveData<Int>()
    var currentStartDay = MutableLiveData<Int>()
    var currentEndYear = MutableLiveData<Int>()
    var currentEndMonth = MutableLiveData<Int>()
    var currentEndDay = MutableLiveData<Int>()

    val startYear = MutableLiveData<Int>()
    val startMonth = MutableLiveData<Int>()
    val startDay = MutableLiveData<Int>()
    var startDate = MutableLiveData<String>()

    val endYear = MutableLiveData<Int>()
    val endMonth = MutableLiveData<Int>()
    val endDay = MutableLiveData<Int>()
    var endDate = MutableLiveData<String>()

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)

    val isTitleAvailable = MutableStateFlow(false)
    var isCheckTripAvailable = MutableLiveData(false)

    fun getMaxTripLen() = MAX_TRIP_LEN

    fun patchTodoToServer() {
        viewModelScope.launch {
            editTripRepository.patchEditTripInfo(
                tripId,
                EditTripRequestModel(
                    title = title.value.orEmpty(),
                    startDate = startDate.value.orEmpty(),
                    endDate = endDate.value.orEmpty()
                )
            )
                .onSuccess {
                    _tripEditState.emit(true)
                }
                .onFailure {
                    _tripEditState.emit(false)
                }
        }
    }

    fun setTitleState(newTitle: String, state: EditTextState) {
        title.value = newTitle
        isTitleAvailable.value = state == EditTextState.SUCCESS
        checkTripAvailable()
    }

    fun setStartDate(year: Int, month: Int, day: Int) {
        startYear.value = year
        startMonth.value = month
        startDay.value = day
        checkStartDateAvailable()
        startDate.value = String.format("%04d.%02d.%02d", year, month, day)
    }


    fun setEndDate(year: Int, month: Int, day: Int) {
        endYear.value = year
        endMonth.value = month
        endDay.value = day
        checkEndDateAvailable()
        endDate.value = String.format("%04d.%02d.%02d", year, month, day)
    }

    fun checkStartDateAvailable() {
        if (startYear.value != null && startMonth.value != null && startDay.value != null) {
            isStartDateAvailable.value = true
            checkTripAvailable()
        } else {
            isStartDateAvailable.value = false
        }
    }

    fun checkEndDateAvailable() {
        if (endYear.value != null && endMonth.value != null && endDay.value != null) {
            isEndDateAvailable.value = true
            checkTripAvailable()
        } else {
            isEndDateAvailable.value = false
        }
    }

    fun checkTripAvailable() {
        isCheckTripAvailable.value = !title.value.isNullOrEmpty() && (
                (title.value != null && currentTitle != title.value) ||
                        currentStartDate != startDate.value ||
                        currentEndDate != endDate.value
                )
    }

    companion object {
        const val MAX_TRIP_LEN = 15
    }
}

