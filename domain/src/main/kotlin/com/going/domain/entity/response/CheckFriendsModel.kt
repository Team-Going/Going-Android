package com.going.domain.entity.response

data class CheckFriendsModel(
    val participants: List<ParticipantsModel>,
    val styles: List<StylesModel>
) {
    data class ParticipantsModel(
        val participantId: Long,
        val name: String,
        val result: Int
    )

    data class StylesModel(
        val rate: Int,
        val isLeft: Boolean
    )
}