package com.going.domain.entity.response

data class CheckFriendsModel(
    val participants: List<TripParticipantModel>,
    val styles: List<StylesModel>
) {
    data class StylesModel(
        val rate: Int,
        val isLeft: Boolean
    )
}