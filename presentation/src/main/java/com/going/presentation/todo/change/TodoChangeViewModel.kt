package com.going.presentation.todo.change

import androidx.lifecycle.ViewModel
import com.going.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoChangeViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

}