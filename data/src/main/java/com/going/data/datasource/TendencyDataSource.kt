package com.going.data.datasource

import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TendencyTestRequestDto

interface TendencyDataSource {
    suspend fun patchTendencyTest(result: TendencyTestRequestDto): NonDataBaseResponse<Any?>
}
