package com.going.data.dto.response

import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.entity.response.TokenReissueModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("userId")
    val userId: Long,
) {
    fun toAuthTokenModel() =
        AuthTokenModel(accessToken = accessToken, refreshToken = refreshToken, userId = userId)

    fun toTokenReissueModel() =
        TokenReissueModel(accessToken, refreshToken)
}
