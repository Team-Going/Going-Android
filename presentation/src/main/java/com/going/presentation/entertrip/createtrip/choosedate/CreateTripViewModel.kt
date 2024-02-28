package com.going.presentation.entertrip.createtrip.choosedate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import com.going.presentation.entertrip.createtrip.preference.ParcelableTripData
import com.going.ui.extension.getGraphemeLength

class CreateTripViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val nameLength = MutableLiveData(0)

    val startYear = MutableLiveData<Int>()
    val startMonth = MutableLiveData<Int>()
    val startDay = MutableLiveData<Int>()

    val endYear = MutableLiveData<Int>()
    val endMonth = MutableLiveData<Int>()
    val endDay = MutableLiveData<Int>()

    var tripIntentData: ParcelableTripData? = null

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)
    val isAvailableDateRange = MutableLiveData<Boolean?>(null)

    val isNameAvailable = MutableLiveData(NameState.Empty)
    private val isTripAvailable = MutableLiveData(false)
    var isCheckTripAvailable = MutableLiveData(false)

    fun checkNameAvailable() {
        nameLength.value = name.value?.getGraphemeLength()

        isNameAvailable.value = when {
            nameLength.value == 0 -> NameState.Empty
            (nameLength.value ?: 0) > MAX_TRIP_LEN -> NameState.OVER
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }

        val isInfoAvailable = nameLength.value in 1..MAX_TRIP_LEN

        isTripAvailable.value = (isNameAvailable.value == NameState.Success) && isInfoAvailable

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
            (isTripAvailable.value == true && isStartDateAvailable.value == true && isEndDateAvailable.value == true)
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
