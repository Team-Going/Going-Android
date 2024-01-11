package com.going.domain.entity.request

data class TodoCreateRequestModel(
    val title: String,
    val endDate: String,
    val allocator: List<Long>,
    val memo: String?,
    val secret: Boolean
)