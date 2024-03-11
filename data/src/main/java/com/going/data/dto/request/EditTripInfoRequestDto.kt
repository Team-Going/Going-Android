package com.going.data.dto.request

import com.going.domain.entity.request.EditTripRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EditTripInfoRequestDto(
    @SerialName("title")
    val title: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
)

fun EditTripRequestModel.toEditTrioRequestDto(): EditTripInfoRequestDto =
    EditTripInfoRequestDto(title, startDate, endDate)

