package com.going.domain.repository

import com.going.domain.entity.request.EnterPreferenceRequestModel

interface EnterPreferenceRepository {

    suspend fun postTipInfo(
        request: EnterPreferenceRequestModel
    ): Result<Unit>

}