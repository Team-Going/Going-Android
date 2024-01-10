package com.going.data.dto.response

import com.going.domain.entity.response.TodoAllocatorModel
import com.going.domain.entity.response.TodoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoResponseDto(
    @SerialName("todoId")
    val todoId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("allocators")
    val allocators: List<TodoAllocatorResponseDto>,
    @SerialName("secret")
    val secret: Boolean
) {

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

    fun toTodoModel() =
        TodoModel(todoId, title, endDate, allocators.map { it.toTodoAllocatorModel() }, secret)
}
