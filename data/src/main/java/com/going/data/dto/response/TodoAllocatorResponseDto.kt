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
    @SerialName("isAllocated")
    val isAllocated: Boolean,
    @SerialName("isOwner")
    val isOwner: Boolean
) {
    fun toTodoAllocatorModel() =
        TodoAllocatorModel(participantId, name, isAllocated, isOwner)
}