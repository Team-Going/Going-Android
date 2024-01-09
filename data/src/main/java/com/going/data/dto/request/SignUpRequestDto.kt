package com.going.data.dto.request

import com.going.domain.entity.request.RequestSignUpModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("intro")
    val intro: String,
    @SerialName("platform")
    val platform: String,
)

fun RequestSignUpModel.toSignUpRequestDto(): SignUpRequestDto =
    SignUpRequestDto(name, intro, platform)
