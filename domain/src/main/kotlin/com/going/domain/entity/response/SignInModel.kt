package com.going.domain.entity.response

data class SignInModel(
    val accessToken: String,
    val refreshToken: String,
    val isResult: Boolean,
    val userId: Long,
)
