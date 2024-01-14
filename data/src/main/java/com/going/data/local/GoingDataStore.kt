package com.going.data.local

interface GoingDataStore {
    var accessToken: String
    var refreshToken: String
    var userId: Long
}
