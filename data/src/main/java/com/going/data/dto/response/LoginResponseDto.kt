package com.going.data.dto.response

import com.going.domain.entity.response.AuthTokenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
) {
    fun toAuthTokenModel() =
        AuthTokenModel(accessToken = accessToken, refreshToken = refreshToken)
}

// @Serializable
// data class LoginResponseDto(
//    @SerialName("status")
//    val status: Int,
//    @SerialName("message")
//    val message: String,
//    @SerialName("data")
//    val data: Data,
// ) {
//    @Serializable
//    data class Data(
//        @SerialName("accessToken")
//        val accessToken: String,
//        @SerialName("refreshToken")
//        val refreshToken: String,
//    )
//
//    fun toAuthTokenModel() =
//        AuthTokenModel(accessToken = data.accessToken, refreshToken = data.refreshToken)
// }
