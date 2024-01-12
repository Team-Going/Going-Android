package com.going.data.service

import com.going.data.dto.NonDataBaseResponse
import retrofit2.http.DELETE
import retrofit2.http.PATCH

interface SettingService {
    @PATCH("api/users/signout")
    suspend fun patchSignOut(): NonDataBaseResponse<Any?>

    @DELETE("api/users/withdraw")
    suspend fun deleteWithDraw(): NonDataBaseResponse<Any?>
}
