package com.going.domain.entity.response

data class OurTripInfoModel(
    val title: String,
    val day: Int,
    val startDate: String,
    val endDate: String,
    val progress: Int,
    val code: String,
    val isComplete: Boolean,
    val participants: List<TripParticipantModel>
)