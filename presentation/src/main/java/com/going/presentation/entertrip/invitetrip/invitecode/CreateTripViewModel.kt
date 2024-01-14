package com.going.presentation.entertrip.invitetrip.invitecode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import java.text.BreakIterator

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
        nameLength.value = getGraphemeLength(name.value)

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

    private fun getGraphemeLength(value: String?): Int {
        BREAK_ITERATOR.setText(value)

        var count = 0
        while (BREAK_ITERATOR.next() != BreakIterator.DONE) {
            count++
        }

        return count
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

    private fun checkTripAvailable() {
        isCheckTripAvailable.value =
            (isTripAvailable.value == true && isStartDateAvailable.value == true && isEndDateAvailable.value == true)
    }


    companion object {
        val BREAK_ITERATOR: BreakIterator = BreakIterator.getCharacterInstance()
        const val MAX_TRIP_LEN = 15
    }
}


