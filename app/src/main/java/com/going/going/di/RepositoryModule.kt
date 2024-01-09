package com.going.going.di

import com.going.data.repositoryImpl.TokenRepositoryImpl
import com.going.data.repositoryImpl.AuthRepositoryImpl
import com.going.data.repositoryImpl.MockRepositoryImpl
import com.going.domain.repository.TokenRepository
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.MockRepository
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
}
