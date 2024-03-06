package com.going.data.dto.response

import com.going.domain.entity.response.ParticipantProfileResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantProfileResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("intro")
    val intro: String,
    @SerialName("result")
    val result: Int,
    @SerialName("styleA")
    val styleA: Int,
    @SerialName("styleB")
    val styleB: Int,
    @SerialName("styleC")
    val styleC: Int,
    @SerialName("styleD")
    val styleD: Int,
    @SerialName("styleE")
    val styleE: Int,
    @SerialName("isOwner")
    val isOwner: Boolean,
) {
    fun toParticipantProfileModel() = ParticipantProfileResponseModel(
        name,
        intro,
        result,
        styleA,
        styleB,
        styleC,
        styleD,
        styleE,
        isOwner
    )
}
