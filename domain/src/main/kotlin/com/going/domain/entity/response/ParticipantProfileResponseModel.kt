package com.going.domain.entity.response

data class ParticipantProfileResponseModel(
    val name: String,
    val intro: String,
    val result: Int,
    val styleA: Int,
    val styleB: Int,
    val styleC: Int,
    val styleD: Int,
    val styleE: Int,
    val isOwner: Boolean,
)
