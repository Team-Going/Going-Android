package com.going.data.dto.response

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
    val allocators: List<TodoListAllocatorResponseDto>,
    @SerialName("secret")
    val secret: Boolean
) {
    fun toTodoModel() =
        TodoModel(todoId, title, endDate, allocators.map { it.toTodoListAllocatorModel() }, secret)
}
