package com.going.presentation.entertrip.createtrip.choosedate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.presentation.designsystem.edittext.EditTextState
import com.going.presentation.entertrip.createtrip.preference.ParcelableTripData
import kotlinx.coroutines.flow.MutableStateFlow

class CreateTripViewModel : ViewModel() {
    val name = MutableLiveData<String>()

    val startYear = MutableLiveData<Int>()
    val startMonth = MutableLiveData<Int>()
    val startDay = MutableLiveData<Int>()

    val endYear = MutableLiveData<Int>()
    val endMonth = MutableLiveData<Int>()
    val endDay = MutableLiveData<Int>()

    var tripIntentData: ParcelableTripData? = null

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)

    val isNameAvailable = MutableStateFlow(false)
    var isCheckTripAvailable = MutableLiveData(false)

    fun getMaxTripLen() = MAX_TRIP_LEN

    fun setTitleState(newTitle: String, state: EditTextState) {
        name.value = newTitle
        isNameAvailable.value = state == EditTextState.SUCCESS
        checkTripAvailable()

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
            checkTripAvailable()
        }
    }

    fun checkEndDateAvailable() {
        if (endYear.value != null && endMonth.value != null && endDay.value != null) {
            isEndDateAvailable.value = true
            checkTripAvailable()
        } else {
            isEndDateAvailable.value = false
            checkTripAvailable()
        }
    }

    fun checkTripAvailable() {
        isCheckTripAvailable.value =
            (isNameAvailable.value == true && isStartDateAvailable.value == true && isEndDateAvailable.value == true)
    }

    fun saveIntentData() {
        tripIntentData = ParcelableTripData(
            name = name.value.orEmpty(),
            startYear = startYear.value ?: 0,
            startMonth = startMonth.value ?: 0,
            startDay = startDay.value ?: 0,
            endYear = endYear.value ?: 0,
            endMonth = endMonth.value ?: 0,
            endDay = endDay.value ?: 0
        )
    }

    companion object {
        const val MAX_TRIP_LEN = 15
    }
}
