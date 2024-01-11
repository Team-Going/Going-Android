package com.going.domain.entity.response

data class TodoDetailModel(
    val title: String,
    val endDate: String,
    val allocators: List<TodoAllocatorModel>,
    val memo: String,
    val secret: Boolean
)