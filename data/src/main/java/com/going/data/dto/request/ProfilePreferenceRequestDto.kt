package com.going.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePreferenceRequestDto(
    @SerialName("styleA")
    val styleA: Int,
    @SerialName("styleB")
    val styleB: Int,
    @SerialName("styleC")
    val styleC: Int,
    @SerialName("styleD")
    val styleD: Int,
    @SerialName("styleE")
    val styleE: Int
)