package com.going.presentation.tendencytest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.domain.entity.TendencyTestMock
import kotlinx.coroutines.flow.MutableStateFlow

class TendencyTestViewModel : ViewModel() {
    val step = MutableStateFlow(1)
    val isChecked = MutableLiveData(false)
    val isFirstChecked = MutableLiveData(false)
    val isSecondChecked = MutableLiveData(false)
    val isThirdChecked = MutableLiveData(false)
    val isFourthChecked = MutableLiveData(false)

    val tendencyResultList: MutableList<Int> = MutableList(9) { 0 }

    val mockList: List<TendencyTestMock> = listOf(
        TendencyTestMock(
            "1번 문항",
            "1-1",
            "1-2",
            "1-3",
            "1-4",
        ),
        TendencyTestMock(
            "2번 문항",
            "2-1",
            "2-2",
            "2-3",
            "2-4",
        ),
        TendencyTestMock(
            "3번 문항",
            "3-1",
            "3-2",
            "3-3",
            "3-4",
        ),
        TendencyTestMock(
            "4번 문항",
            "4-1",
            "4-2",
            "4-3",
            "4-4",
        ),
        TendencyTestMock(
            "5번 문항",
            "5-1",
            "5-2",
            "5-3",
            "5-4",
        ),
        TendencyTestMock(
            "6번 문항",
            "6-1",
            "6-2",
            "6-3",
            "6-4",
        ),
        TendencyTestMock(
            "7번 문항",
            "7-1",
            "7-2",
            "7-3",
            "7-4",
        ),
        TendencyTestMock(
            "8번 문항",
            "8-1",
            "8-2",
            "8-3",
            "8-4",
        ),
        TendencyTestMock(
            "9번 문항",
            "9-1",
            "9-2",
            "9-3",
            "9-4",
        ),
    )

    fun plusStepValue() {
        step.value = step.value.plus(1)
    }

    fun setCheckedValue(id: Int) {
        clearAllChecked()
        isChecked.value = true
        tendencyResultList[step.value - 1] = id

        when (id) {
            1 -> isFirstChecked.value = true
            2 -> isSecondChecked.value = true
            3 -> isThirdChecked.value = true
            4 -> isFourthChecked.value = true
        }
    }

    fun clearAllChecked() {
        isChecked.value = false
        isFirstChecked.value = false
        isSecondChecked.value = false
        isThirdChecked.value = false
        isFourthChecked.value = false
    }

    companion object {
        const val MAX_STEP = 9
    }
}
