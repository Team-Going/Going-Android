package com.going.domain.repository

import com.going.domain.entity.request.SignInRequestModel
import com.going.domain.entity.request.SignUpRequestModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.entity.response.SignInModel

interface AuthRepository {
    suspend fun postSignIn(
        Authorization: String,
        data: SignInRequestModel,
    ): Result<SignInModel>

    suspend fun postSignUp(
        Authorization: String,
        data: SignUpRequestModel,
    ): Result<AuthTokenModel>

    suspend fun getSplash(): Result<Unit>
}
