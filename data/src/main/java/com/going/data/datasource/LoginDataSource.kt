package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.RequestLoginDto
import com.going.data.dto.response.LoginResponseDto

interface LoginDataSource {
    suspend fun postLogin(
        Authorization: String,
        platform: RequestLoginDto,
    ): BaseResponse<LoginResponseDto>
}
