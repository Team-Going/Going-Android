package com.going.going.di

import android.content.Context
import android.content.SharedPreferences
import com.going.data.local.GoingDataStoreImpl
import com.going.data.local.GoingDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideGoingDataStore(dataStoreImpl: GoingDataStoreImpl): GoingDataStore =
        dataStoreImpl
}
