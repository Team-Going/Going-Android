package com.going.data.dto.response

import com.going.domain.entity.response.OurTripInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OurTripInfoResponseDto(
    @SerialName("title")
    val title: String,
    @SerialName("day")
    val day: Int,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("progress")
    val progress: Int,
    @SerialName("code")
    val code: String,
    @SerialName("isComplete")
    val isComplete: Boolean,
    @SerialName("participants")
    val participants: List<TripParticipantResponseDto>
) {
    fun toOurTripInfoModel() : OurTripInfoModel =
        OurTripInfoModel(title, day, startDate, endDate, progress, code, isComplete, participants.map { it.toTripParticipantModel() })
}