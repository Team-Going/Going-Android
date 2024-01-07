package com.going.presentation.createtrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateTripViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val NameLength = MutableLiveData(0)

    val StartDate = MutableLiveData<String>()
    val EndDate = MutableLiveData<String>()

    val isNameAvailable = MutableLiveData<NameState>(NameState.Empty)
    val isProfileAvailable = MutableLiveData(false)

    private val _ButtonAvailable = MutableStateFlow(false)
    val ButtonAvailable: StateFlow<Boolean> = _ButtonAvailable

    fun getMaxNameLen() = CreateTripViewModel.MAX_TRIP_LEN

    fun checkNameAvailable() {
        NameLength.value = getNameLength(name.value)

        isNameAvailable.value = when {
            NameLength.value == 0 -> NameState.Empty
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
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
