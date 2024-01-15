package com.going.data.repositoryImpl

import com.going.data.datasource.TokenReissueDataSource
import com.going.data.dto.request.toTokenReissueModel
import com.going.domain.entity.request.TokenReissueRequestModel
import com.going.domain.entity.response.TokenReissueModel
import com.going.domain.repository.TokenReissueRepository
import javax.inject.Inject

class TokenReissueRepositoryImpl @Inject constructor(
    private val tokenReissueDataSource: TokenReissueDataSource,
) : TokenReissueRepository {
    override suspend fun postReissueTokens(request: TokenReissueRequestModel): Result<TokenReissueModel> =
        runCatching {
            tokenReissueDataSource.postReissueTokens(request.toTokenReissueModel()).data.toTokenReissueModel()
        }
}
