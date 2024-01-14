package com.going.data.datasourceImpl

import com.going.data.datasource.EnterPreferenceDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.request.EnterPreferenceRequestDto
import com.going.data.dto.response.EnterPreferenceResponseDto
import com.going.data.service.EnterPreferenceService
import javax.inject.Inject

class EnterPreferenceDataSourceImpl @Inject constructor(
    private val enterPreferenceService: EnterPreferenceService
) : EnterPreferenceDataSource {

    override suspend fun postTripInfo(
        request: EnterPreferenceRequestDto
    ): BaseResponse<EnterPreferenceResponseDto> =
        enterPreferenceService.postTripInfoFromServer(request)

}