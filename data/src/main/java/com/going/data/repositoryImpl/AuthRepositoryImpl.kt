package com.going.data.repositoryImpl

import com.going.data.datasource.AuthDataSource
import com.going.data.dto.request.toSignInRequestDto
import com.going.data.dto.request.toSignUpRequestDto
import com.going.domain.entity.request.RequestSignInModel
import com.going.domain.entity.request.RequestSignUpModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postSignIn(
        Authorization: String,
        requestSignIpModel: RequestSignInModel,
    ): Result<AuthTokenModel> =
        runCatching {
            authDataSource.postSignIn(
                Authorization,
                requestSignIpModel.toSignInRequestDto(),
            ).data.toAuthTokenModel()
        }

    override suspend fun postSignUp(
        Authorization: String,
        requestSignUpModel: RequestSignUpModel,
    ): Result<AuthTokenModel> =
        runCatching {
            authDataSource.postSignUp(
                Authorization,
                requestSignUpModel.toSignUpRequestDto(),
            ).data.toAuthTokenModel()
        }
}
