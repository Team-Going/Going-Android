package com.going.domain.repository

import com.going.domain.entity.request.RequestSignInModel
import com.going.domain.entity.request.RequestSignUpModel
import com.going.domain.entity.response.AuthTokenModel

interface AuthRepository {
    suspend fun postSignIn(
        Authorization: String,
        data: RequestSignInModel,
    ): Result<AuthTokenModel>

    suspend fun postSignUp(
        Authorization: String,
        data: RequestSignUpModel,
    ): Result<AuthTokenModel>
}
