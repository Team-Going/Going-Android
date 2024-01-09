package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.SignInRequestDto
import com.going.data.dto.request.SignUpRequestDto
import com.going.data.dto.response.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/users/signin")
    suspend fun postSignin(
        @Header("Authorization") Authorization: String,
        @Body body: SignInRequestDto,
    ): BaseResponse<AuthResponseDto>

    @POST("api/users/signup")
    suspend fun postSignUp(
        @Body body: SignUpRequestDto,
    ): BaseResponse<AuthResponseDto>
}
