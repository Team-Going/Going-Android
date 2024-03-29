package com.going.domain.entity.response

data class TodoModel(
    val todoId: Long,
    val title: String,
    val endDate: String,
    val allocators: List<TodoListAllocatorModel>,
    val secret: Boolean
)
