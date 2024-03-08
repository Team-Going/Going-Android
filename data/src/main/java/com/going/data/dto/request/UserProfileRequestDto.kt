package com.going.data.dto.request

import com.going.domain.entity.request.TokenReissueRequestModel
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.entity.response.UserProfileResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("intro")
    val intro: String,
)

fun UserProfileRequestModel.toDto() = UserProfileRequestDto(name, intro)

