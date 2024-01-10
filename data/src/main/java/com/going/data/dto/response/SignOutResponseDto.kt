package com.going.data.dto.response

import com.going.domain.entity.response.SignOutModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignOutResponseDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
) {
    fun toSignOutModel() =
        SignOutModel(status, message)
}
