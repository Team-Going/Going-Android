package com.going.data.dto.response

import com.going.domain.entity.response.TodoDetailModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoDetailResponseDto(
    @SerialName("title")
    val title: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("allocators")
    val allocators: List<TodoAllocatorResponseDto>,
    @SerialName("memo")
    val memo: String,
    @SerialName("secret")
    val secret: Boolean
) {
    fun toTodoDetailModel(): TodoDetailModel =
        TodoDetailModel(title, endDate, allocators.map { it.toTodoAllocatorModel() }, memo, secret)
}