package com.going.data.repositoryImpl

import com.going.data.datasource.TendencyDataSource
import com.going.data.dto.request.toTendencyTestRequestDto
import com.going.domain.entity.request.TendencyRequestModel
import com.going.domain.repository.TendencyRepository
import javax.inject.Inject

class TendencyRepositoryImpl @Inject constructor(
    private val tendencyDataSource: TendencyDataSource,
) : TendencyRepository {

    override suspend fun patchTendencyTest(
        result: TendencyRequestModel
    ): Result<Unit> =
        runCatching {
            tendencyDataSource.patchTendencyTest(result.toTendencyTestRequestDto())
        }
}
