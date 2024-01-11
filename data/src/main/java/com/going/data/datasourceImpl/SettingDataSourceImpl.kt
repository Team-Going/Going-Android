package com.going.data.datasourceImpl

import com.going.data.datasource.SettingDataSource
import com.going.data.dto.NullableBaseResponse
import com.going.data.dto.response.SignOutResponseDto
import com.going.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingService: SettingService,
) : SettingDataSource {
    override suspend fun patchSignOut(): SignOutResponseDto = settingService.patchSignOut()
    override suspend fun deleteWithDraw(): NullableBaseResponse<String?> = settingService.deleteWithDraw()
}
