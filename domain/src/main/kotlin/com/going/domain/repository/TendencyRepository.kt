package com.going.domain.repository

import com.going.domain.entity.request.TendencyRequestModel

interface TendencyRepository {
    suspend fun patchTendencyTest(result: TendencyRequestModel): Result<Unit>
}
