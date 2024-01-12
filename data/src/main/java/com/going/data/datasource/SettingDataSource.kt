package com.going.data.datasource

import com.going.data.dto.NonDataBaseResponse

interface SettingDataSource {
    suspend fun patchSignOut(): NonDataBaseResponse

    suspend fun deleteWithDraw(): NonDataBaseResponse
}
