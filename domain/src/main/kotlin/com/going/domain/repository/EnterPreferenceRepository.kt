package com.going.domain.repository

import com.going.domain.entity.request.EnterPreferenceRequestModel
import com.going.domain.entity.response.EnterPreferenceModel

interface EnterPreferenceRepository {

    suspend fun postTripInfo(
        request: EnterPreferenceRequestModel
    ): Result<EnterPreferenceModel>

}