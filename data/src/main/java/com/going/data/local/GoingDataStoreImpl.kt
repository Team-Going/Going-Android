package com.going.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class GoingDataStoreImpl @Inject constructor(
    private val dataStore: SharedPreferences,
) : GoingDataStore {
    override var accessToken: String
        get() = dataStore.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = dataStore.edit { putString(ACCESS_TOKEN, value) }

    override var refreshToken: String
        get() = dataStore.getString(REFRESH_TOKEN, "") ?: ""
        set(value) = dataStore.edit { putString(REFRESH_TOKEN, value) }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}
