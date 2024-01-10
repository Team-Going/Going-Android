package com.going.doorip.di

import com.going.data.datasource.AuthDataSource
import com.going.data.datasource.MockDataSource
import com.going.data.datasource.SettingDataSource
import com.going.data.datasourceImpl.AuthDataSourceImpl
import com.going.data.datasourceImpl.MockDataSourceImpl
import com.going.data.datasourceImpl.SettingDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMockDataSource(mockDataSourceImpl: MockDataSourceImpl): MockDataSource =
        mockDataSourceImpl

    @Provides
    @Singleton
    fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource =
        authDataSourceImpl

    @Provides
    @Singleton
    fun provideSettingDataSource(settingDataSourceImpl: SettingDataSourceImpl): SettingDataSource =
        settingDataSourceImpl
}
