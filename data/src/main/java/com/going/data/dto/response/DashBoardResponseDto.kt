package com.going.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashBoardResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("trips")
    val trips: List<TripsResponseDto>

) {
    @Serializable
    data class TripsResponseDto(
        @SerialName("tripId")
        val tripId: Int,
        @SerialName("title")
        val title: String,
        @SerialName("startDate")
        val startDate: String,
        @SerialName("endDate")
        val endDate: String,
        @SerialName("day")
        val day: Int
    )
}

