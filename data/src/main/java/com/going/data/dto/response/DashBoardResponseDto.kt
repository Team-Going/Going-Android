package com.going.data.dto.response

import com.going.domain.entity.response.DashBoardModel
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
        val tripId: Long,
        @SerialName("title")
        val title: String,
        @SerialName("startDate")
        val startDate: String,
        @SerialName("endDate")
        val endDate: String,
        @SerialName("day")
        val day: Int
    ) {

        fun toTripsModel() =
            DashBoardModel.DashBoardTripModel(tripId, title, startDate, endDate, day)
    }

    fun toDashBoardModel() =
        DashBoardModel(name, trips.map {
            it.toTripsModel()
        })
}

