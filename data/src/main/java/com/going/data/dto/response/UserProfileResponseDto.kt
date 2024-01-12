package com.going.data.dto.response

import com.going.domain.entity.request.UserProfileRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("intro")
    val intro: String,
    @SerialName("result")
    val result: Int,
) {
    fun toProfileModel() = UserProfileRequestModel(name, intro, result)
}
