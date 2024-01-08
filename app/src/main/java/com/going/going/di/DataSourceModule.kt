package com.going.going.di

import com.going.data.datasource.LoginDataSource
import com.going.data.datasource.MockDataSource
import com.going.data.datasourceImpl.LoginDataSourceImpl
import com.going.data.datasourceImpl.MockDataSourceImpl
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
    fun provideLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource =
        loginDataSourceImpl


}
