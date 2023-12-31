package com.going.domain.entity.response

data class AuthTokenModel(
    val isResigned: Boolean,
    val accessToken: String,
    val refreshToken: String,
)
