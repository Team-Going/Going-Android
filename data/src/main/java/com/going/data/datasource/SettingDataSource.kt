package com.going.data.datasource

import com.going.data.dto.NonDataBaseResponse

interface SettingDataSource {
    suspend fun patchSignOut(): NonDataBaseResponse<Any?>

    suspend fun deleteWithDraw(): NonDataBaseResponse<Any?>
}
