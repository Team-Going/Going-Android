package com.going.data.repositoryImpl

import com.going.data.datasource.TokenReissueDataSource
import com.going.data.dto.request.toTokenReissueModel
import com.going.domain.entity.request.TokenReissueRequestModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.TokenReissueRepository
import javax.inject.Inject

class TokenReissueRepositoryImpl @Inject constructor(
    private val tokenReissueDataSource: TokenReissueDataSource,
) : TokenReissueRepository {

    override suspend fun postReissueTokens(
        authorization: String,
        request: TokenReissueRequestModel,
    ): Result<AuthTokenModel> =
        runCatching {
            tokenReissueDataSource.postReissueTokens(
                authorization,
                request.toTokenReissueModel(),
            ).data.toAuthTokenModel()
        }
}
