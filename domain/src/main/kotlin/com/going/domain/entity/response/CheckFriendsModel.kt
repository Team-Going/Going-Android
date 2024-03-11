package com.going.domain.entity.response

data class CheckFriendsModel(
    val bestPrefer: List<String>,
    val participants: List<TripParticipantModel>,
    val styles: List<StylesModel>
) {
    data class StylesModel(
        val rates: List<Int>,
        val counts: List<Int>
    )
}
