package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.SignInRequestDto
import com.going.data.dto.request.SignUpRequestDto
import com.going.data.dto.response.AuthResponseDto
import com.going.data.dto.response.SignInResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/users/signin")
    suspend fun postSignin(
        @Header("Authorization") Authorization: String,
        @Body body: SignInRequestDto,
    ): BaseResponse<SignInResponseDto>

    @POST("api/users/signup")
    suspend fun postSignUp(
        @Header("Authorization") Authorization: String,
        @Body body: SignUpRequestDto,
    ): BaseResponse<AuthResponseDto>
}
