package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.PreferenceChangeRequestDto
import com.going.data.dto.request.UserProfileRequestDto
import com.going.data.dto.response.ParticipantProfileResponseDto
import com.going.data.dto.response.UserProfileResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ProfileService {
    @GET("api/users/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfileResponseDto>

    @PATCH("api/users/profile")
    suspend fun patchUserProfile(
        @Body request: UserProfileRequestDto,
    ): NonDataBaseResponse

    @GET("api/trips/participants/{participantId}")
    suspend fun getParticipantProfile(
        @Path("participantId") participantId: Long
    ): BaseResponse<ParticipantProfileResponseDto>

    @PATCH("api/trips/{tripId}/participant")
    suspend fun patchPreferenceTag(
        @Path("tripId") tripId: Long,
        @Body request: PreferenceChangeRequestDto
    ): NonDataBaseResponse
}
