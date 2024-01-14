package com.going.data.datasource

import com.going.data.dto.request.EnterPreferenceRequestDto
import com.going.data.dto.response.EnterPreferenceResponseDto
import retrofit2.http.Body

interface EnterPreferenceDataSource {
    suspend fun postTripInfo(
        @Body request: EnterPreferenceRequestDto
    ): EnterPreferenceResponseDto
}