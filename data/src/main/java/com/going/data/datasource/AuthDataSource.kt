package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.SignInRequestDto
import com.going.data.dto.request.SignUpRequestDto
import com.going.data.dto.response.AuthResponseDto

interface AuthDataSource {
    suspend fun postSignIn(
        Authorization: String,
        platform: SignInRequestDto,
    ): BaseResponse<AuthResponseDto>

    suspend fun postSignUp(
        Authorization: String,
        data: SignUpRequestDto,
    ): BaseResponse<AuthResponseDto>
}
