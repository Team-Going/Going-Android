package com.going.data.datasourceImpl

import com.going.data.datasource.LoginDataSource
import com.going.data.dto.request.RequestLoginDto
import com.going.data.dto.response.ResponseLoginDto
import com.going.data.service.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
) : LoginDataSource {
    override suspend fun postLogin(
        Authorization: String,
        platform: RequestLoginDto,
    ): ResponseLoginDto =
        loginService.postSignin(Authorization, platform)
}
