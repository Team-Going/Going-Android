package com.going.domain.entity.response

data class TripParticipantsListModel(
    val participant: List<TripParticipantModel>,
    val styleA: Int,
    val styleB: Int,
    val styleC: Int,
    val styleD: Int,
    val styleE: Int,
)
