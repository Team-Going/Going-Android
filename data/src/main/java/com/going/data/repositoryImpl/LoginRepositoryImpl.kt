package com.going.data.repositoryImpl

import com.going.data.datasource.LoginDataSource
import com.going.data.dto.request.RequestLoginDto
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun postSignin(
        Authorization: String,
        platform: String,
    ): Result<AuthTokenModel> =
        runCatching {
            loginDataSource.postLogin(
                Authorization,
                RequestLoginDto(platform),
            ).data.toAuthTokenModel()
        }
}
