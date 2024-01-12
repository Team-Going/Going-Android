package com.going.data.datasourceImpl

import com.going.data.datasource.AuthDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.SignInRequestDto
import com.going.data.dto.request.SignUpRequestDto
import com.going.data.dto.response.AuthResponseDto
import com.going.data.dto.response.SignInResponseDto
import com.going.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postSignIn(
        Authorization: String,
        platform: SignInRequestDto,
    ): BaseResponse<SignInResponseDto> =
        authService.postSignin(Authorization, platform)

    override suspend fun postSignUp(
        Authorization: String,
        data: SignUpRequestDto,
    ): BaseResponse<AuthResponseDto> = authService.postSignUp(Authorization, data)

    override suspend fun getSplash(): NonDataBaseResponse = authService.getSplash()
}
