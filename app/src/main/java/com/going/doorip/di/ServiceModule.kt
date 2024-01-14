package com.going.doorip.di

import com.going.data.service.AuthService
import com.going.data.service.DashBoardService
import com.going.data.service.EnterTripService
import com.going.data.service.MockService
import com.going.data.service.ProfileService
import com.going.data.service.SettingService
import com.going.data.service.TendencyService
import com.going.data.service.TodoService
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
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideSettingService(retrofit: Retrofit): SettingService =
        retrofit.create(SettingService::class.java)

    @Provides
    @Singleton
    fun provideTodoService(retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun provideDashBoardService(retrofit: Retrofit): DashBoardService =
        retrofit.create(DashBoardService::class.java)

    @Provides
    @Singleton
    fun provideEnterTripService(retrofit: Retrofit): EnterTripService =
        retrofit.create(EnterTripService::class.java)

    @Provides
    @Singleton
    fun provideTendencyService(retrofit: Retrofit): TendencyService =
        retrofit.create(TendencyService::class.java)

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)

}
