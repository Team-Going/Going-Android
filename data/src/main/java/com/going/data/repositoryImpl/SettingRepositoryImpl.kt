package com.going.data.repositoryImpl

import com.going.data.datasource.SettingDataSource
import com.going.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource,
) : SettingRepository {
    override suspend fun patchSignOut(): Result<Unit> = runCatching {
        settingDataSource.patchSignOut()
    }

    override suspend fun deleteWithDraw(): Result<Unit> = runCatching {
        settingDataSource.deleteWithDraw()
    }
}
