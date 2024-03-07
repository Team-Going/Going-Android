package com.going.domain.entity.request

data class TripInfoRequestModel(
    val tripId: Long,
    val title: String,
    val startDate : String,
    val endDate : String,
)
