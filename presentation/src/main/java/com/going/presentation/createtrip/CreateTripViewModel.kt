package com.going.presentation.createtrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateTripViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val NameLength = MutableLiveData(0)

    val StartYear = MutableLiveData<Int>()
    val StartMonth = MutableLiveData<Int>()
    val StartDay = MutableLiveData<Int>()

    val EndYear = MutableLiveData<Int>()
    val EndMonth = MutableLiveData<Int>()
    val EndDay = MutableLiveData<Int>()

    val isStartDateAvailable = MutableLiveData(false)
    val isEndDateAvailable = MutableLiveData(false)

    val isNameAvailable = MutableLiveData<NameState>(NameState.Empty)
    var isCheckTripAvailable = MutableLiveData(false)

    private val _ButtonAvailable = MutableStateFlow(false)
    val ButtonAvailable: StateFlow<Boolean> = _ButtonAvailable


    fun checkNameAvailable() {
        NameLength.value = getNameLength(name.value)

        isNameAvailable.value = when {
            NameLength.value == 0 -> NameState.Empty
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }
    }

    fun checkStartDateAvailable() {
        if (StartYear.value != null && StartMonth.value != null && StartDay.value != null) {
            isStartDateAvailable.value = true
        } else {
            isStartDateAvailable.value = false
        }
    }

    fun checkEndDateAvailable() {
        if (EndYear.value != null && EndMonth.value != null && EndDay.value != null) {
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
