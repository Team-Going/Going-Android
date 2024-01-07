package com.going.data.service

import com.going.data.dto.request.RequestLoginDto
import com.going.data.dto.response.ResponseLoginDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("api/users/signin")
    suspend fun postSignin(
        @Header("Authorization") Authorization: String,
        @Body body: RequestLoginDto,
    ): ResponseLoginDto
}
