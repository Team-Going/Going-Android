package com.going.data.dto.response

import com.going.domain.entity.response.EnterTripModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnterTripResponseDto(
    @SerialName("tripId")
    val tripId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("day")
    val day: Int,
) {
    fun toEnterTripModel() =
        EnterTripModel(tripId, title, startDate, endDate, day)
}
