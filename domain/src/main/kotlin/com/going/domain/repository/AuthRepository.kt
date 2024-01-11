package com.going.domain.repository

import com.going.domain.entity.request.RequestSignInModel
import com.going.domain.entity.request.RequestSignUpModel
import com.going.domain.entity.response.AuthTokenModel
import com.going.domain.entity.response.SignInModel

interface AuthRepository {
    suspend fun postSignIn(
        Authorization: String,
        data: RequestSignInModel,
    ): Result<SignInModel>

    suspend fun postSignUp(
        Authorization: String,
        data: RequestSignUpModel,
    ): Result<AuthTokenModel>
}
