package com.going.going.di

import com.going.data.repositoryImpl.AuthRepositoryImpl
import com.going.data.repositoryImpl.MockRepositoryImpl
import com.going.data.repositoryImpl.SettingRepositoryImpl
import com.going.data.repositoryImpl.TokenRepositoryImpl
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.MockRepository
import com.going.domain.repository.SettingRepository
import com.going.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMockRepository(mockRepositoryImpl: MockRepositoryImpl): MockRepository =
        mockRepositoryImpl

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository =
        tokenRepositoryImpl

    @Provides
    @Singleton
    fun provideSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository =
        settingRepositoryImpl
}
