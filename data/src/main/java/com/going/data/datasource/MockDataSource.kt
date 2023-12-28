package com.going.data.datasource

import com.going.data.dto.response.MockFollowerResponseDto

interface MockDataSource {

    suspend fun getFollowerListData(
        page: Int
    ): MockFollowerResponseDto

}