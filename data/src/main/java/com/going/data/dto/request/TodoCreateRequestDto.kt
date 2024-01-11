package com.going.data.dto.request

import com.going.domain.entity.request.TodoCreateRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoCreateRequestDto(
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

fun TodoCreateRequestModel.toTodoCreateRequestDto(): TodoCreateRequestDto =
    TodoCreateRequestDto(title, endDate, allocators, memo, secret)