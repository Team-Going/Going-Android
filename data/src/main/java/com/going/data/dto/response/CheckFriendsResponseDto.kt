package com.going.data.dto.response

import com.going.domain.entity.response.CheckFriendsModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckFriendsResponseDto(
    @SerialName("participants")
    val participants: List<TripParticipantResponseDto>,
    @SerialName("styles")
    val styles: List<StylesResponseDto>
) {

    @Serializable
    data class StylesResponseDto(
        @SerialName("rate")
        val rate: Int,
        @SerialName("isLeft")
        val isLeft: Boolean
    ) {
        fun toStyleModel() =
            CheckFriendsModel.StylesModel(rate, isLeft)
    }

    fun toCheckFriendsModel() =
        CheckFriendsModel(participants.map {
            it.toTripParticipantModel()
        }, styles.map {
            it.toStyleModel()
        })

}
