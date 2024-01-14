package com.going.data.dto.response

import com.going.domain.entity.response.SignInModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isResult")
    val isResult: Boolean,
    @SerialName("userId")
    val userId: Long,
) {
    fun toSignInModel() =
        SignInModel(accessToken, refreshToken, isResult, userId)
}
