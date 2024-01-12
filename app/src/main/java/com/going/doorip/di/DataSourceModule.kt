package com.going.doorip.di

import com.going.data.datasource.AuthDataSource
import com.going.data.datasource.EnterTripDataSource
import com.going.data.datasource.MockDataSource
import com.going.data.datasource.SettingDataSource
import com.going.data.datasource.TendencyDataSource
import com.going.data.datasource.TodoDataSource
import com.going.data.datasourceImpl.AuthDataSourceImpl
import com.going.data.datasourceImpl.EnterTripDataSourceImpl
import com.going.data.datasourceImpl.MockDataSourceImpl
import com.going.data.datasourceImpl.SettingDataSourceImpl
import com.going.data.datasourceImpl.TendencyDataSourceImpl
import com.going.data.datasourceImpl.TodoDataSourceImpl
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

    @Provides
    @Singleton
    fun provideTodoDataSource(todoDataSourceImpl: TodoDataSourceImpl): TodoDataSource =
        todoDataSourceImpl

    @Provides
    @Singleton
    fun provideTendencyDataSource(tendencyDataSourceImpl: TendencyDataSourceImpl): TendencyDataSource =
        tendencyDataSourceImpl

    @Provides
    @Singleton
    fun provideEnterTripDataSource(entertripDataSourceImpl: EnterTripDataSourceImpl): EnterTripDataSource =
        entertripDataSourceImpl

}
