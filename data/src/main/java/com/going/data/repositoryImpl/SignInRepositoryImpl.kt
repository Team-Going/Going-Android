package com.going.data.repositoryImpl

import com.going.data.datasource.SignInDataSource
import com.going.data.dto.request.RequestSignInDto
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.SignInRepository
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
) : SignInRepository {
    override suspend fun postSignIn(
        Authorization: String,
        platform: String,
    ): Result<AuthTokenModel> =
        runCatching {
            signInDataSource.postLogin(
                Authorization,
                RequestSignInDto(platform),
            ).data.toAuthTokenModel()
        }
}
