package com.going.data.repositoryImpl

import com.going.data.datasource.EnterPreferenceDataSource
import com.going.data.dto.request.toEnterPreferenceRequestDto
import com.going.domain.entity.request.EnterPreferenceRequestModel
import com.going.domain.entity.response.EnterPreferenceModel
import com.going.domain.repository.EnterPreferenceRepository
import javax.inject.Inject

class EnterPreferenceRepositoryImpl @Inject constructor(
    private val enterPreferenceDataSource: EnterPreferenceDataSource
) : EnterPreferenceRepository {

    override suspend fun postTripInfo(
        request: EnterPreferenceRequestModel
    ): Result<EnterPreferenceModel> =
        runCatching {
            enterPreferenceDataSource.postTripInfo(
                request.toEnterPreferenceRequestDto(),
            ).data.toEnterPreferenceModel()
        }


}
