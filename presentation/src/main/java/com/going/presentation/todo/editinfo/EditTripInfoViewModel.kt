package com.going.presentation.todo.editinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.presentation.designsystem.edittext.EditTextState
import kotlinx.coroutines.flow.MutableStateFlow

class EditTripInfoViewModel : ViewModel() {
    val name = MutableLiveData<String>()

    val startYear = MutableLiveData<Int>()
    val startMonth = MutableLiveData<Int>()
    val startDay = MutableLiveData<Int>()

    val endYear = MutableLiveData<Int>()
    val endMonth = MutableLiveData<Int>()
    val endDay = MutableLiveData<Int>()

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)

    val isNameAvailable = MutableStateFlow(false)
    var isCheckTripAvailable = MutableLiveData(false)

    fun getMaxTripLen() = MAX_TRIP_LEN

    fun setNameState(newName: String, state: EditTextState) {
        name.value = newName
        isNameAvailable.value = state == EditTextState.SUCCESS
    }

    fun setStartDate(year: Int, month: Int, day: Int) {
        startYear.value = year
        startMonth.value = month
        startDay.value = day
        checkStartDateAvailable()
    }

    fun setEndDate(year: Int, month: Int, day: Int) {
        endYear.value = year
        endMonth.value = month
        endDay.value = day
        checkEndDateAvailable()
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
        isCheckTripAvailable.value =
            (isNameAvailable.value == true && isStartDateAvailable.value == true && isEndDateAvailable.value == true)
    }

    companion object {
        const val MAX_TRIP_LEN = 15
    }
}

