package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.RequestSignInDto
import com.going.data.dto.response.ResponseSignInDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("api/users/signin")
    suspend fun postSignin(
        @Header("Authorization") Authorization: String,
        @Body body: RequestSignInDto,
    ): BaseResponse<ResponseSignInDto>
}
