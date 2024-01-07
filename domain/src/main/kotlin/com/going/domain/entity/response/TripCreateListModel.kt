package com.going.domain.entity.response

data class TripCreateListModel(
    val tripId : Long,
    val title : String,
    val startDate : String,
    val endDate : String,
    val code : String,
    val day : Int
)
