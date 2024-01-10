package com.going.presentation.entertrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.CodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EnterTripViewModel : ViewModel() {

    val inviteCode = MutableLiveData<String>()
    val codeLength = MutableLiveData(0)

    val isCodeAvailable = MutableLiveData(CodeState.Empty)
    var isCheckEnterAvailable = MutableLiveData(false)

    private val _ButtonAvailable = MutableStateFlow(false)
    val ButtonAvailable: StateFlow<Boolean> = _ButtonAvailable

    fun checkCodeAvailable() {
        val codeLength = getCodeLength(inviteCode.value)

        isCodeAvailable.value = when {
            codeLength == 0 -> CodeState.Empty
            inviteCode.value.isNullOrBlank() -> CodeState.Blank
            !isCodeValid(inviteCode.value) -> CodeState.Invalid
            else -> CodeState.Success.also { checkEnterAvailable() }
        }
    }

    private fun getCodeLength(value: String?) = value?.length

    private fun isCodeValid(code: String?) = code?.matches("^[a-z0-9]*$".toRegex()) ?: false

    fun checkEnterAvailable() {
        isCheckEnterAvailable.value = isCodeAvailable.value == CodeState.Success
    }


    fun setButtonAvailable() {
        _ButtonAvailable.value = true
    }

    companion object {
        const val MAX_INVITE_LEN = 6
    }
}