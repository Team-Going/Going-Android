package com.going.presentation.todo.mytodo.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyTodoDetailViewModel : ViewModel() {

    val todo = MutableLiveData("")
    val nowTodoLength = MutableLiveData(0)

    val endDate = MutableLiveData("")

    val memo = MutableLiveData("")
    val nowMemoLength = MutableLiveData(0)

    companion object {
        const val MAX_TODO_LEN = 15
        const val MAX_MEMO_LEN = 1000
    }

}