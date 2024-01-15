package com.going.data.datasourceImpl

import com.going.data.datasource.TokenReissueDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.request.TokenReissueRequestDto
import com.going.data.dto.response.AuthResponseDto
import com.going.data.service.TokenReissueService
import javax.inject.Inject

data class TokenReissueDataSourceImpl @Inject constructor(
    private val tokenReissueService: TokenReissueService,
) : TokenReissueDataSource {
    override suspend fun postReissueTokens(userId: TokenReissueRequestDto): BaseResponse<AuthResponseDto> =
        tokenReissueService.postReissueTokens(userId)
}
