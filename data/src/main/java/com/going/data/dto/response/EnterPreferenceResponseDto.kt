package com.going.data.dto.response

import com.going.domain.entity.response.EnterPreferenceModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
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
        EnterPreferenceModel(tripId, title, startDate, endDate, code, day)
}
