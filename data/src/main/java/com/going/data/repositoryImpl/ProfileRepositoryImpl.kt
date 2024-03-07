package com.going.data.repositoryImpl

import com.going.data.datasource.ProfileDataSource
import com.going.data.dto.request.toParticipantRequestDto
import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getUserProfile(): Result<UserProfileRequestModel> =
        runCatching {
            profileDataSource.getUserProfile().data.toProfileModel()
        }

    override suspend fun patchUserProfile(): Result<UserProfileRequestModel> =
        kotlin.runCatching {
            profileDataSource.patchUserProfile().data.toProfileModel()
        }

    override suspend fun getParticipantProfile(
        participantProfileRequestModel: ParticipantProfileRequestModel
    ): Result<ParticipantProfileResponseModel> =
        runCatching {
            profileDataSource.getParticipantProfile(participantProfileRequestModel.toParticipantRequestDto()).data.toParticipantProfileModel()
        }
}
