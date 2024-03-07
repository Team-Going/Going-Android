package com.going.data.dto.response

import com.going.domain.entity.request.TripInfoRequestModel
import com.going.domain.entity.response.TripInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripInfoResponseDto(
    @SerialName("tripId")
    val tripId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
) {
    fun toTripInfoModel(): TripInfoModel
    = TripInfoModel(tripId, title, startDate, endDate)
}
