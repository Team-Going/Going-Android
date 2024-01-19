package com.going.doorip.di

import com.going.data.service.AuthService
import com.going.data.service.DashBoardService
import com.going.data.service.EnterTripService
import com.going.data.service.ProfileService
import com.going.data.service.SettingService
import com.going.data.service.TendencyService
import com.going.data.service.TodoService
import com.going.data.service.TokenReissueService
import com.going.doorip.di.qualifier.JWT
import com.going.doorip.di.qualifier.REISSUE
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
    fun provideAuthService(@JWT retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideSettingService(@JWT retrofit: Retrofit): SettingService =
        retrofit.create(SettingService::class.java)

    @Provides
    @Singleton
    fun provideTodoService(@JWT retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun provideDashBoardService(@JWT retrofit: Retrofit): DashBoardService =
        retrofit.create(DashBoardService::class.java)

    @Provides
    @Singleton
    fun provideEnterTripService(@JWT retrofit: Retrofit): EnterTripService =
        retrofit.create(EnterTripService::class.java)

    @Provides
    @Singleton
    fun provideTendencyService(@JWT retrofit: Retrofit): TendencyService =
        retrofit.create(TendencyService::class.java)

    @Provides
    @Singleton
    fun provideProfileService(@JWT retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)

    @Provides
    @Singleton
    fun provideTokenReissueService(@REISSUE retrofit: Retrofit): TokenReissueService =
        retrofit.create(TokenReissueService::class.java)
}
