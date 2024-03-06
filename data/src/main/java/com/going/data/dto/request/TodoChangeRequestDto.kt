package com.going.data.dto.request

import com.going.domain.entity.request.TodoChangeRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoChangeRequestDto(
    @SerialName("title")
    val title: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("allocators")
    val allocators: List<Long>,
    @SerialName("memo")
    val memo: String?,
    @SerialName("secret")
    val secret: Boolean
)

fun TodoChangeRequestModel.toTodoChangeRequestDto(): TodoChangeRequestDto =
    TodoChangeRequestDto(title, endDate, allocators, memo, secret)