package com.going.presentation.todo.mytodo.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.BreakIterator

class MyTodoCreateViewModel : ViewModel() {

    val todo = MutableLiveData("")
    val nowTodoLength = MutableLiveData(0)

    val endDate = MutableLiveData("")

    val memo = MutableLiveData("")
    val nowMemoLength = MutableLiveData(0)

    val isFinishAvailable = MutableLiveData(false)

    fun getMaxTodoLen() = MAX_TODO_LEN

    fun getMaxMemoLen() = MAX_MEMO_LEN

    fun checkIsFinishAvailable() {
        nowTodoLength.value = getGraphemeLength(todo.value)
        nowMemoLength.value = getGraphemeLength(memo.value)
        isFinishAvailable.value =
            todo.value?.isNotEmpty() == true && memo.value?.isNotEmpty() == true && endDate.value?.isNotEmpty() == true
    }

    // 이모지 포함 글자 수 세는 함수
    private fun getGraphemeLength(value: String?): Int {
        BREAK_ITERATOR.setText(value)
        var count = 0
        while (BREAK_ITERATOR.next() != BreakIterator.DONE) {
            count++
        }
        return count
    }

    companion object {
        val BREAK_ITERATOR: BreakIterator = BreakIterator.getCharacterInstance()

        const val MAX_TODO_LEN = 15
        const val MAX_MEMO_LEN = 1000
    }

}