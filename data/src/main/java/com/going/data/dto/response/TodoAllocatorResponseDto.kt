package com.going.data.dto.response

import com.going.domain.entity.response.TodoAllocatorModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoAllocatorResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("isOwner")
    val isOwner: Boolean
) {
    fun toTodoAllocatorModel() =
        TodoAllocatorModel(name, isOwner)
}