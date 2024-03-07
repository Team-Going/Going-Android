package com.going.data.dto.response

import com.going.domain.entity.response.TodoListAllocatorModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoListAllocatorResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("isOwner")
    val isOwner: Boolean
) {
    fun toTodoListAllocatorModel() =
        TodoListAllocatorModel(name, isOwner)
}