package com.going.data.datasourceImpl

import com.going.data.datasource.SettingDataSource
import com.going.data.dto.NonDataBaseResponse
import com.going.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingService: SettingService,
) : SettingDataSource {
    override suspend fun patchSignOut(): NonDataBaseResponse =
        settingService.patchSignOut()

    override suspend fun deleteWithDraw(): NonDataBaseResponse =
        settingService.deleteWithDraw()
}
