package com.going.domain.entity.response

data class EnterPreferenceModel(
    val tripId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val code: String,
    val day: Int,
)
