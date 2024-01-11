package com.going.data.service

import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.response.SignOutResponseDto
import retrofit2.http.DELETE
import retrofit2.http.PATCH

interface SettingService {
    @PATCH("api/users/signout")
    suspend fun patchSignOut(): SignOutResponseDto

    @DELETE("api/users/withdraw")
    suspend fun deleteWithDraw(): NonDataBaseResponse<Unit>
}
