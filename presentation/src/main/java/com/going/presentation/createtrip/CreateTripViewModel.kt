package com.going.presentation.createtrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.NameState
import com.going.presentation.onboarding.OnboardingProfileSettingViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.BreakIterator

class CreateTripViewModel : ViewModel() {
    val name = MutableLiveData(String())
    val nowNameLength = MutableLiveData(0)

    val isNameAvailable = MutableLiveData(NameState.Empty)
    val isProfileAvailable = MutableLiveData(false)


    private val _isMoveScreenAvailable = MutableStateFlow(false)
    val isMoveScreenAvailable: StateFlow<Boolean> = _isMoveScreenAvailable


    fun getMaxNameLen() = OnboardingProfileSettingViewModel.MAX_NAME_LEN

    fun checkProfileAvailable() {
        nowNameLength.value = getGraphemeLength(name.value)

        isNameAvailable.value = when {
            nowNameLength.value == 0 -> NameState.Empty
            name.value.isNullOrBlank() -> NameState.Blank
            else -> NameState.Success
        }

        val isInfoAvailable = getGraphemeLength(info.value) in 1..OnboardingProfileSettingViewModel.MAX_INFO_LEN

        isProfileAvailable.value =
            (isNameAvailable.value == NameState.Success) && isInfoAvailable
    }

    // 이모지 포함 글자 수 세는 함수
    private fun getGraphemeLength(value: String?): Int {
        OnboardingProfileSettingViewModel.BREAK_ITERATOR.setText(value)

        var count = 0
        while (OnboardingProfileSettingViewModel.BREAK_ITERATOR.next() != BreakIterator.DONE) {
            count++
        }

        return count
    }
    fun setIsMoveScreenAvailable() {
        _isMoveScreenAvailable.value = true
    }
    companion object {
        val BREAK_ITERATOR: BreakIterator = BreakIterator.getCharacterInstance()

        const val MAX_NAME_LEN = 3
    }
}