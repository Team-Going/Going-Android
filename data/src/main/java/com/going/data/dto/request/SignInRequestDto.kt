package com.going.data.dto.request

import com.going.domain.entity.request.SignInRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    @SerialName("platform")
    val platform: String,
)

fun SignInRequestModel.toSignInRequestDto(): SignInRequestDto =
    SignInRequestDto(platform)
