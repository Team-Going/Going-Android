package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.UserProfileResponseDto
import retrofit2.http.GET

interface ProfileService {
    @GET("api/users/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto>
}
