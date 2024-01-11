package com.going.domain.repository

import com.going.domain.entity.response.SignOutModel

interface SettingRepository {
    suspend fun patchSignOut(): Result<SignOutModel>

    suspend fun deleteWithDraw(): Result<String?>
}
