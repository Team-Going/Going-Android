package com.going.data.dto.response

import kotlinx.serialization.SerialName

data class EnterPreferenceResponseDto(
    @SerialName("tripId")
    val tripId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("code")
    val code: String,
    @SerialName("day")
    val day: Int,
) {
    fun toEnterPreferenceModel() =
        EnterPreferenceResponseDto(tripId, title, startDate, endDate, code, day)
}
