package com.going.doorip.di

import com.going.data.repositoryImpl.AuthRepositoryImpl
import com.going.data.repositoryImpl.DashBoardRepositoryImpl
import com.going.data.repositoryImpl.EnterTripRepositoryImpl
import com.going.data.repositoryImpl.MockRepositoryImpl
import com.going.data.repositoryImpl.ProfileRepositoryImpl
import com.going.data.repositoryImpl.SettingRepositoryImpl
import com.going.data.repositoryImpl.TendencyRepositoryImpl
import com.going.data.repositoryImpl.TodoRepositoryImpl
import com.going.data.repositoryImpl.TokenRepositoryImpl
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.DashBoardRepository
import com.going.domain.repository.EnterTripRepository
import com.going.domain.repository.MockRepository
import com.going.domain.repository.ProfileRepository
import com.going.domain.repository.SettingRepository
import com.going.domain.repository.TendencyRepository
import com.going.domain.repository.TodoRepository
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

    @Provides
    @Singleton
    fun provideTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository =
        todoRepositoryImpl

    @Provides
    @Singleton
    fun providesDashBoardRepository(dashBoardRepositoryImpl: DashBoardRepositoryImpl): DashBoardRepository =
        dashBoardRepositoryImpl

    @Provides
    @Singleton
    fun provideEnterTripRepository(entertripRepositoryImpl: EnterTripRepositoryImpl): EnterTripRepository =
        entertripRepositoryImpl

    @Provides
    @Singleton
    fun provideTendencyRepository(tendencyRepositoryImpl: TendencyRepositoryImpl): TendencyRepository =
        tendencyRepositoryImpl

    @Provides
    @Singleton
    fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository =
        profileRepositoryImpl

}
