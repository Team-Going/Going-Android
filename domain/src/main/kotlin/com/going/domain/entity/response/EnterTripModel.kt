package com.going.domain.entity.response

data class EnterTripModel(
    val tripId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val day: Int
)
