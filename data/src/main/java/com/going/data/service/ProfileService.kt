package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.request.ParticipantProfileRequestDto
import com.going.data.dto.response.ParticipantProfileResponseDto
import com.going.data.dto.response.UserProfileResponseDto
import com.going.domain.entity.request.ParticipantProfileRequestModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("api/users/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto>

    @GET("api/trips/participants/{participantId}")
    suspend fun getParticipantProfile(
        @Path("participantId") participantId: ParticipantProfileRequestDto
    ): BaseResponse<ParticipantProfileResponseDto>
}
