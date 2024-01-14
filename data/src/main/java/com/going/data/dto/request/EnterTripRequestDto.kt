package com.going.data.dto.request

import com.going.domain.entity.request.EnterTripRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnterTripRequestDto(
    @SerialName("code")
    val code: String,
)

fun EnterTripRequestModel.toEnterTripRequestDto(): EnterTripRequestDto =
    EnterTripRequestDto(code)
