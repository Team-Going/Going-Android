package com.going.domain.repository

import com.going.domain.entity.request.TokenReissueRequestModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.entity.response.TokenReissueModel

interface TokenReissueRepository {
    suspend fun postReissueTokens(
        authorization: String,
        request: TokenReissueRequestModel,
    ): Result<AuthTokenModel>
}
