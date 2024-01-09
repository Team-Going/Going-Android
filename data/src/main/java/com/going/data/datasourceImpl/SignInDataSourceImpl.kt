package com.going.data.datasourceImpl

import com.going.data.datasource.SignInDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.request.RequestSignInDto
import com.going.data.dto.response.ResponseSignInDto
import com.going.data.service.LoginService
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
) : SignInDataSource {
    override suspend fun postLogin(
        Authorization: String,
        platform: RequestSignInDto,
    ): BaseResponse<ResponseSignInDto> =
        loginService.postSignin(Authorization, platform)
}
