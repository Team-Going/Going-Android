package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.TokenReissueRequestDto
import com.going.data.dto.response.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenReissueService {
    @POST("api/users/reissue")
    suspend fun postReissueTokens(
        @Header("Authorization") authorization: String,
        @Body request: TokenReissueRequestDto,
    ): BaseResponse<AuthResponseDto>
}
