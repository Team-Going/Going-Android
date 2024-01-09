package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.SignInRequestDto
import com.going.data.dto.request.SignUpRequestDto
import com.going.data.dto.response.AuthResponseDto

interface AuthDataSource {
    suspend fun postLogin(
        Authorization: String,
        platform: SignInRequestDto,
    ): BaseResponse<AuthResponseDto>

    suspend fun postSignUp(
        platform: SignUpRequestDto,
    ): BaseResponse<AuthResponseDto>
}
