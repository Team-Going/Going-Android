package com.going.data.dto.response

import com.going.domain.entity.response.CheckFriendsModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckFriendsResponseDto(
    @SerialName("bestPrefer")
    val bestPrefer: List<String>,
    @SerialName("participants")
    val participants: List<TripParticipantResponseDto>,
    @SerialName("styles")
    val styles: List<StylesResponseDto>
) {
    @Serializable
    data class StylesResponseDto(
        @SerialName("rates")
        val rates: List<Int>,
        @SerialName("counts")
        val counts: List<Int>
    ) {
        fun toStyleModel() =
            CheckFriendsModel.StylesModel(rates, counts)
    }

    fun toCheckFriendsModel() =
        CheckFriendsModel(
            bestPrefer,
            participants.map {
                it.toTripParticipantModel()
            }, styles.map {
                it.toStyleModel()
            })

}
