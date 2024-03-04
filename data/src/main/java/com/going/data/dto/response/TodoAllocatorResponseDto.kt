package com.going.data.dto.response

import com.going.domain.entity.response.TodoAllocatorModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoAllocatorResponseDto(
    @SerialName("participantId")
    val participantId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("isOwner")
    val isOwner: Boolean,
    @SerialName("isAllocated")
    val isAllocated: Boolean
) {
    fun toTodoAllocatorModel() =
        TodoAllocatorModel(participantId, name, isOwner, isAllocated)
}