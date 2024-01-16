package com.going.domain.entity.request

data class EnterPreferenceRequestModel(
    val title: String,
    val startDate: String,
    val endDate: String,
    val styleA: Int,
    val styleB: Int,
    val styleC: Int,
    val styleD: Int,
    val styleE: Int
)
