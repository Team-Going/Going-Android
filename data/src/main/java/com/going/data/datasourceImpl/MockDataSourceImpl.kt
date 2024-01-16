package com.going.data.datasourceImpl

import com.going.data.datasource.MockDataSource
import com.going.data.dto.response.MockFollowerResponseDto
import com.going.data.service.MockService
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val mockService: MockService
) : MockDataSource {

    override suspend fun getFollowerListData(
        page: Int
    ): MockFollowerResponseDto =
        mockService.getFollowerList(page)

}