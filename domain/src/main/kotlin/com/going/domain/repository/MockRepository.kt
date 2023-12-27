package com.going.domain.repository

import com.going.domain.entity.response.MockFollowerModel

interface MockRepository {

    suspend fun getFollowerList(
        page: Int
    ): Result<List<MockFollowerModel>>

}