package com.going.presentation.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.BreakIterator

class OnboardingProfileSettingViewModel : ViewModel() {
    val name = MutableLiveData(String())
    val info = MutableLiveData(String())

    // 추후 해당 값을 활용하여 텍스트박스에 변화를 줄 것 예측샷 ㅋㅋ
    val isNameAvailable = MutableLiveData(false)
    val isInfoAvailable = MutableLiveData(false)

    private val _isProfileAvailable = MutableStateFlow(true)
    val isProfileAvailable: StateFlow<Boolean> = _isProfileAvailable

    fun checkProfileAvailable() {
        isNameAvailable.value =
            (getGraphemeLength(name.value) <= MAX_NAME_LEN) && name.value.isNullOrBlank()
        isInfoAvailable.value = getGraphemeLength(info.value) in 1..MAX_INFO_LEN

        _isProfileAvailable.value = name.value.isNullOrBlank() && info.value.isNullOrBlank()
    }

    // 이모지 포함 글자 수 세는 함수
    private fun getGraphemeLength(value: String?): Int {
        breakIterator.setText(value)

        var count = 0
        while (breakIterator.next() != BreakIterator.DONE) {
            count++
        }

        return count
    }

    companion object {
        val breakIterator: BreakIterator = BreakIterator.getCharacterInstance()

        const val MAX_NAME_LEN = 5
        const val MAX_INFO_LEN = 20
    }
}
