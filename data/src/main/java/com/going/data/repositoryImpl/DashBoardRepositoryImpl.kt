package com.going.data.repositoryImpl

import com.going.data.datasource.DashBoardDataSource
import com.going.domain.entity.response.DashBoardModel
import com.going.domain.repository.DashBoardRepository
import javax.inject.Inject

class DashBoardRepositoryImpl @Inject constructor(
    private val dashBoardSource: DashBoardDataSource
) : DashBoardRepository {
    override suspend fun getDashBoardList(
        progress: String
    ): Result<DashBoardModel> =
        runCatching {
            dashBoardSource.getTripList(progress).data.toDashBoardEntity()
        }
}