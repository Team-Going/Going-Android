package com.going.data.datasource

import com.going.data.dto.request.RequestLoginDto
import com.going.data.dto.response.ResponseLoginDto

interface LoginDataSource {
    suspend fun postLogin(
        Authorization: String,
        platform: RequestLoginDto,
    ): ResponseLoginDto
}
