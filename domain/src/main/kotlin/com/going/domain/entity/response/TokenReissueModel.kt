package com.going.domain.entity.response

data class TokenReissueModel(
    val accessToken: String,
    val refreshToken: String,
)
