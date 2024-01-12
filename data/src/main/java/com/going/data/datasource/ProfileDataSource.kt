package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.UserProfileResponseDto

interface ProfileDataSource {
    suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto>
}
