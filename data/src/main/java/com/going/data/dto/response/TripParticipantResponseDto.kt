package com.going.data.dto.response

import com.going.domain.entity.response.TripParticipantModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripParticipantResponseDto(
    @SerialName("participantId")
    val participantId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("result")
    val result: Int
) {
    fun toTripParticipantModel() : TripParticipantModel =
        TripParticipantModel(participantId, name, result)
}