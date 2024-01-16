package com.going.domain.entity.response

data class TripParticipantModel(
    val participantId: Long,
    val name: String,
    val result: Int,
    var isSelected: Boolean = false
)
