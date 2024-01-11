package com.going.presentation.enter.entertrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.CodeState
import java.util.regex.Pattern

class EnterTripViewModel : ViewModel() {

    val inviteCode = MutableLiveData<String>()
    var codeLength = MutableLiveData(0)

    val isCodeAvailable = MutableLiveData(CodeState.Empty)
    var isCheckEnterAvailable = MutableLiveData(false)

    fun checkCodeAvailable() {
        codeLength.value = getCodeLength(inviteCode.value)
        isCodeAvailable.value = when {
            codeLength.value == 0 -> CodeState.Empty
            inviteCode.value.isNullOrBlank() -> CodeState.Blank
            !isCodeValid(inviteCode.value) -> CodeState.Invalid
            else -> CodeState.Success.also { checkEnterAvailable() }
        }
    }

    private fun getCodeLength(value: String?) = value?.length ?: 0

    private fun isCodeValid(code: String?) = code?.matches(ENG_NUM_REGEX.toRegex()) ?: false

    fun checkEnterAvailable() {
        isCheckEnterAvailable.value = isCodeAvailable.value == CodeState.Success
    }


    companion object {
        private const val ENG_NUM_PATTERN = "^[a-z0-9]*$"
        val ENG_NUM_REGEX: Pattern = Pattern.compile(ENG_NUM_PATTERN)
        const val MAX_INVITE_LEN = 6
    }
}
