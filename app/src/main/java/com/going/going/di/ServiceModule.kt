package com.going.going.di

import com.going.data.service.AuthService
import com.going.data.service.MockService
import com.going.going.di.qualifier.KAKAO
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
    fun provideMockService(@KAKAO retrofit: Retrofit): MockService =
        retrofit.create(MockService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(@KAKAO retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
