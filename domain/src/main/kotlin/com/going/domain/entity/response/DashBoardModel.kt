package com.going.domain.entity.response

data class DashBoardModel(
    val name : String,
    val trips : List<TripsModel>
){
    data class TripsModel(
        val tripId : Long,
        val title : String,
        val startDate : String,
        val endDate : String,
        val day : Int
    )
}