package com.going.going.di

import com.going.data.repositoryImpl.AuthRepositoryImpl
import com.going.data.repositoryImpl.SignInRepositoryImpl
import com.going.data.repositoryImpl.MockRepositoryImpl
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.SignInRepository
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
    fun provideLoginRepository(loginRepositoryImpl: SignInRepositoryImpl): SignInRepository =
        loginRepositoryImpl

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl
}
