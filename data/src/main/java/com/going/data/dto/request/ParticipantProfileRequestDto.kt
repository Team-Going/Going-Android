package com.going.data.dto.request

import com.going.domain.entity.request.ParticipantProfileRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantProfileRequestDto(
    @SerialName("participantId")
    val participantId: Long,
)

fun ParticipantProfileRequestModel.toParticipantRequestDto(): ParticipantProfileRequestDto =
    ParticipantProfileRequestDto(participantId)
