package com.going.data.dto.response

import com.going.domain.entity.response.MyTripInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyTripInfoResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("count")
    val count: Int
) {
    fun toMyTripInfoModel() =
        MyTripInfoModel(name, count)
}