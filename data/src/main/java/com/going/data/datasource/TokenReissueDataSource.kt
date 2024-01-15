package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.TokenReissueRequestDto
import com.going.data.dto.response.AuthResponseDto

interface TokenReissueDataSource {

    suspend fun postReissueTokens(
        userId: TokenReissueRequestDto,
    ): BaseResponse<AuthResponseDto>
}
