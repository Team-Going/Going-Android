package com.going.presentation.entertrip.createtrip.choosedate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
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

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)

    val isNameAvailable = MutableLiveData(NameState.Empty)
    val isTripAvailable = MutableLiveData(false)
    var isCheckTripAvailable = MutableLiveData(false)

    fun getMaxNameLen() = MAX_TRIP_LEN

    fun checkNameAvailable() {
        nameLength.value = name.value?.getGraphemeLength()

        isNameAvailable.value = when {
            nameLength.value == 0 -> NameState.Empty
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }

        val isInfoAvailable = nameLength.value in 1..MAX_TRIP_LEN

        isTripAvailable.value =
            (isNameAvailable.value == NameState.Success) && isInfoAvailable

        checkTripAvailable()
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

    private fun checkTripAvailable() {
        isCheckTripAvailable.value =
            (isTripAvailable.value == true && isStartDateAvailable.value == true && isEndDateAvailable.value == true)
    }

    companion object {
        const val MAX_TRIP_LEN = 15
    }
}