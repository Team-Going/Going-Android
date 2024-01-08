package com.going.presentation.starttrip.createtrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

    val isNameAvailable = MutableLiveData<NameState>(NameState.Empty)
    var isCheckTripAvailable = MutableLiveData(false)

    private val _ButtonAvailable = MutableStateFlow(false)
    val ButtonAvailable: StateFlow<Boolean> = _ButtonAvailable

    fun checkNameAvailable() {
        nameLength.value = getNameLength(name.value)

        isNameAvailable.value = when {
            nameLength.value == 0 -> NameState.Empty
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }
    }

    fun checkStartDateAvailable() {
        isStartDateAvailable.value = (startYear.value != null && startMonth.value != null && startDay.value != null)
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
        if (isNameAvailable.value == NameState.Success && isStartDateAvailable.value == true && isEndDateAvailable.value == true) {
            isCheckTripAvailable.value = true
        } else {
            isCheckTripAvailable.value = false
        }
    }

    private fun getNameLength(value: String?): Int {
        return value?.length ?: 0
    }

    fun setButtonAvailable() {
        _ButtonAvailable.value = true
    }

    companion object {
        const val MAX_TRIP_LEN = 15
    }
}


