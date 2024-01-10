package com.going.data.datasource

import com.going.data.dto.response.SignOutResponseDto

interface SettingDataSource {
    suspend fun patchSignOut(): SignOutResponseDto
}
