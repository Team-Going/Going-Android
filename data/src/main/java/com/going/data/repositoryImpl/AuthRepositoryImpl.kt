package com.going.data.repositoryImpl

import com.going.data.datasource.AuthDataSource
import com.going.data.dto.request.SignInRequestDto
import com.going.data.dto.request.toSignUpRequestDto
import com.going.domain.entity.request.RequestSignUpModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postSignIn(
        Authorization: String,
        platform: String,
    ): Result<AuthTokenModel> =
        runCatching {
            authDataSource.postLogin(
                Authorization,
                SignInRequestDto(platform),
            ).data.toAuthTokenModel()
        }

    override suspend fun postSignUp(
        requestSignUpModel: RequestSignUpModel,
    ): Result<AuthTokenModel> =
        runCatching {
            authDataSource.postSignUp(
                requestSignUpModel.toSignUpRequestDto(),
            ).data.toAuthTokenModel()
        }
}
