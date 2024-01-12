package com.going.data.repositoryImpl

import com.going.data.datasource.AuthDataSource
import com.going.data.dto.request.toSignInRequestDto
import com.going.data.dto.request.toSignUpRequestDto
import com.going.domain.entity.request.SignInRequestModel
import com.going.domain.entity.request.SignUpRequestModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.entity.response.SignInModel
import com.going.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postSignIn(
        Authorization: String,
        requestSignIpModel: SignInRequestModel,
    ): Result<SignInModel> =
        runCatching {
            authDataSource.postSignIn(
                Authorization,
                requestSignIpModel.toSignInRequestDto(),
            ).data.toSignInModel()
        }

    override suspend fun postSignUp(
        Authorization: String,
        signUpRequestModel: SignUpRequestModel,
    ): Result<AuthTokenModel> =
        runCatching {
            authDataSource.postSignUp(
                Authorization,
                signUpRequestModel.toSignUpRequestDto(),
            ).data.toAuthTokenModel()
        }

    override suspend fun getSplash(): Result<Unit> = kotlin.runCatching {
        authDataSource.getSplash()
    }
}
