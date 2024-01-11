package com.going.data.dto.request

import com.going.domain.entity.request.TendencyRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TendencyTestRequestDto(
    @SerialName("result")
    val result: List<Int>,
)

fun TendencyRequestModel.toTendencyTestRequestDto() = TendencyTestRequestDto(result)
