package com.going.doorip.di

import com.going.data.service.AuthService
import com.going.data.service.MockService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideMockService(retrofit: Retrofit): MockService =
        retrofit.create(MockService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
