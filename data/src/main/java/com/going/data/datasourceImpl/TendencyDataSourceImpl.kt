package com.going.data.datasourceImpl

import com.going.data.datasource.TendencyDataSource
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TendencyTestRequestDto
import com.going.data.service.TendencyService
import javax.inject.Inject

class TendencyDataSourceImpl @Inject constructor(
    private val tendencyService: TendencyService,
) : TendencyDataSource {
    override suspend fun patchTendencyTest(result: TendencyTestRequestDto): NonDataBaseResponse<Any?> =
        tendencyService.patchTendencyTest(result)
}
