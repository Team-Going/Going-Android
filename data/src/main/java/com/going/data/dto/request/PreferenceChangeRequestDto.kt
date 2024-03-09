package com.going.data.dto.request

import com.going.domain.entity.request.PreferenceChangeRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreferenceChangeRequestDto(
    @SerialName("styleA")
    val styleA: Int,
    @SerialName("styleB")
    val styleB: Int,
    @SerialName("styleC")
    val styleC: Int,
    @SerialName("styleD")
    val styleD: Int,
    @SerialName("styleE")
    val styleE: Int
)

fun PreferenceChangeRequestModel.toPreferenceChangeRequestDto(): PreferenceChangeRequestDto =
    PreferenceChangeRequestDto(styleA, styleB, styleC, styleD, styleE)
