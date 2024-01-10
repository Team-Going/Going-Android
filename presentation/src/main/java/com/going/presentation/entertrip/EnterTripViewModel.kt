package com.going.presentation.entertrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EnterTripViewModel : ViewModel() {

    val inviteCode = MutableLiveData<String>()
    val codeLength = MutableLiveData(0)

    val isCodeAvailable = MutableLiveData<NameState>(NameState.Empty)
    var isCheckEnterAvailable = MutableLiveData(false)

    private val _ButtonAvailable = MutableStateFlow(false)
    val ButtonAvailable: StateFlow<Boolean> = _ButtonAvailable

    fun checkCodeAvailable() {
        codeLength.value = getNameLength(inviteCode.value)

        isCodeAvailable.value = when { //서버에서 받은 초대코드와 비교
            codeLength.value == 0 -> NameState.Empty
            inviteCode.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success.also { checkEnterAvailable() }
        }
    }

    private fun getNameLength(value: String?): Int {
        return value?.length ?: 0
    }

    fun checkEnterAvailable() {
        isCheckEnterAvailable.value = isCodeAvailable.value == NameState.Success
    }


    fun setButtonAvailable() {
        _ButtonAvailable.value = true
    }

    companion object {
        const val MAX_INVITE_LEN = 6
    }
}