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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    var title: String? = null

    var currentStartYear: Int? = null
    var currentStartMonth: Int? = null
    var currentStartDay: Int? = null
    var currentEndYear: Int? = null
    var currentEndMonth: Int? = null
    var currentEndDay: Int? = null

    var startYear: Int? = null
    var startMonth: Int? = null
    var startDay: Int? = null
    var startDate = MutableLiveData<String>()

    var endYear: Int? = null
    var endMonth: Int? = null
    var endDay: Int? = null
    var endDate = MutableLiveData<String>()

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)

    val isTitleAvailable = MutableStateFlow(false)
    var isCheckTripAvailable = MutableLiveData(false)

    fun getMaxTripLen() = MAX_TRIP_LEN

    fun patchTripInfoFromServer() {
        viewModelScope.launch {
            editTripRepository.patchEditTripInfo(
                tripId,
                EditTripRequestModel(
                    title = title.orEmpty(),
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

    fun splitStartDate() {
        val (startYear, startMonth, startDay) = splitDate(currentStartDate)
        currentStartYear = startYear
        currentStartMonth = startMonth
        currentStartDay = startDay
        setStartDate(startYear, startMonth, startDay)

        val (endYear, endMonth, endDay) = splitDate(currentEndDate)
        currentEndYear = endYear
        currentEndMonth = endMonth
        currentEndDay = endDay
        setEndDate(endYear, endMonth, endDay)
    }

    fun splitEndDate() {
        val (endYear, endMonth, endDay) = splitDate(currentEndDate)
        currentEndYear = endYear
        currentEndMonth = endMonth
        currentEndDay = endDay
        setEndDate(endYear, endMonth, endDay)
    }

    fun splitDate(date: String): Triple<Int, Int, Int> {
        val parts = date.split(".")
        val year = parts[0].toInt()
        val month = parts[1].toInt()
        val day = parts[2].toInt()
        return Triple(year, month, day)
    }

    fun setTitleState(newTitle: String, state: EditTextState) {
        title = newTitle
        isTitleAvailable.value = state == EditTextState.SUCCESS
        checkTripAvailable(
            title,
            currentTitle,
            startDate,
            endDate,
            currentStartDate,
            currentEndDate
        )
    }

    fun setStartDate(year: Int, month: Int, day: Int) {
        startYear = year
        startMonth = month
        startDay = day
        checkStartDateAvailable()
        startDate.value = String.format("%04d.%02d.%02d", year, month, day)
    }


    fun setEndDate(year: Int, month: Int, day: Int) {
        endYear = year
        endMonth = month
        endDay = day
        checkEndDateAvailable()
        endDate.value = String.format("%04d.%02d.%02d", year, month, day)
    }

    fun checkStartDateAvailable() {
        if (startYear != null && startMonth != null && startDay != null) {
            isStartDateAvailable.value = true
            checkTripAvailable(
                title,
                currentTitle,
                startDate,
                endDate,
                currentStartDate,
                currentEndDate
            )
        } else {
            isStartDateAvailable.value = false
        }
    }

    fun checkEndDateAvailable() {
        if (endYear != null && endMonth != null && endDay != null) {
            isEndDateAvailable.value = true
            checkTripAvailable(
                title,
                currentTitle,
                startDate,
                endDate,
                currentStartDate,
                currentEndDate
            )
        } else {
            isEndDateAvailable.value = false
        }
    }

    fun checkTripAvailable(
        title: String?,
        currentTitle: String?,
        startDate: MutableLiveData<String>,
        endDate: MutableLiveData<String>,
        currentStartDate: String,
        currentEndDate: String
    ) {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val currentStart = LocalDate.parse(currentStartDate, formatter)
        val currentEnd = LocalDate.parse(currentEndDate, formatter)
        val start = startDate.value?.let { LocalDate.parse(it, formatter) }
        val end = endDate.value?.let { LocalDate.parse(it, formatter) }

        val titleCondition = !title.isNullOrEmpty() && title != currentTitle
        val startDateCondition = start != null && start != currentStart && start.isBefore(currentEnd)
        val endDateCondition = end != null && end != currentEnd && currentStart.isBefore(end)
        val bothDatesCondition = start != null && end != null && start != currentStart && end != currentEnd && start.isBefore(end)

        isCheckTripAvailable.value = titleCondition || startDateCondition || endDateCondition || bothDatesCondition
    }


    companion object {
        const val MAX_TRIP_LEN = 15
    }
}

