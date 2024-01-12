package com.going.data.dto.request

import com.going.domain.entity.request.RequestEnterTripModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnterTripRequestDto(
    @SerialName("code")
    val code: String,
)

fun RequestEnterTripModel.toEnterTripRequestDto(): EnterTripRequestDto =
    EnterTripRequestDto(code)

