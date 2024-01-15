package com.going.data.dto.request

import com.going.domain.entity.request.TokenReissueRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenReissueRequestDto(
    @SerialName("userId")
    val userId: Long,
)

fun TokenReissueRequestModel.toTokenReissueModel() = TokenReissueRequestDto(userId)
