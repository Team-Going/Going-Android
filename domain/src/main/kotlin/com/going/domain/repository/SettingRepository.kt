package com.going.domain.repository

interface SettingRepository {
    suspend fun patchSignOut(): Result<Unit>

    suspend fun deleteWithDraw(): Result<Unit>
}
