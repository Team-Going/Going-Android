package com.going.data.datasource

import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.response.SignOutResponseDto

interface SettingDataSource {
    suspend fun patchSignOut(): SignOutResponseDto

    suspend fun deleteWithDraw(): NonDataBaseResponse<Unit>
}
