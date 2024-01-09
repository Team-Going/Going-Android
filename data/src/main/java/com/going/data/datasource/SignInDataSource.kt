package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.RequestSignInDto
import com.going.data.dto.response.ResponseSignInDto

interface SignInDataSource {
    suspend fun postLogin(
        Authorization: String,
        platform: RequestSignInDto,
    ): BaseResponse<ResponseSignInDto>
}
