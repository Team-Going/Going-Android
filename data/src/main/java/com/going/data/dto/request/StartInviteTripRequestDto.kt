package com.going.data.dto.request

import com.going.domain.entity.request.StartInviteTripRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StartInviteTripRequestDto(
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
)

fun StartInviteTripRequestModel.toStartInviteTripRequestDto(): StartInviteTripRequestDto =
    StartInviteTripRequestDto(styleA, styleB, styleC, styleD, styleE)
