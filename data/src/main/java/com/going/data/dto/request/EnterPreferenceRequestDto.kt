package com.going.data.dto.request

import com.going.domain.entity.request.EnterPreferenceRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnterPreferenceRequestDto(
    @SerialName("title")
    val title: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
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

fun EnterPreferenceRequestModel.toEnterPreferenceRequestDto(): EnterPreferenceRequestDto =
    EnterPreferenceRequestDto(title, startDate, endDate, styleA, styleB, styleC, styleD, styleE)
