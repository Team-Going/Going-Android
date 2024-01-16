package com.going.data.repositoryImpl

import com.going.data.datasource.MockDataSource
import com.going.domain.entity.response.MockFollowerModel
import com.going.domain.repository.MockRepository
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource
) : MockRepository {

    override suspend fun getFollowerList(
        page: Int
    ): Result<List<MockFollowerModel>> =
        runCatching {
            mockDataSource.getFollowerListData(page).toMockFollowerModel()
        }

}