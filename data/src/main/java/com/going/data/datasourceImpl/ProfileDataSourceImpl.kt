package com.going.data.datasourceImpl

import com.going.data.datasource.ProfileDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.response.UserProfileResponseDto
import com.going.data.service.ProfileService
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileService: ProfileService,
) : ProfileDataSource {
    override suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto> =
        profileService.getUserProfile()
}
