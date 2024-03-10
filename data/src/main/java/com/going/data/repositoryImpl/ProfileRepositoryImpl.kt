package com.going.data.repositoryImpl

import com.going.data.datasource.ProfileDataSource
import com.going.data.dto.request.toDto
import com.going.data.dto.request.toParticipantRequestDto
import com.going.data.dto.request.toPreferenceChangeRequestDto
import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.request.PreferenceChangeRequestModel
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.domain.entity.response.UserProfileResponseModel
import com.going.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getUserProfile(): Result<UserProfileResponseModel> =
        runCatching {
            profileDataSource.getUserProfile().data.toProfileModel()
        }

    override suspend fun patchUserProfile(userProfileResponseModel: UserProfileRequestModel): Result<Unit> =
        runCatching {
            profileDataSource.patchUserProfile(userProfileResponseModel.toDto())
        }


    override suspend fun getParticipantProfile(
        participantProfileRequestModel: ParticipantProfileRequestModel
    ): Result<ParticipantProfileResponseModel> =
        runCatching {
            profileDataSource.getParticipantProfile(participantProfileRequestModel.toParticipantRequestDto()).data.toParticipantProfileModel()
        }

    override suspend fun patchPreferenceTag(
        tripId: Long,
        request: PreferenceChangeRequestModel
    ): Result<Unit> =
        runCatching {
            profileDataSource.patchPreferenceTag(
                tripId,
                request.toPreferenceChangeRequestDto()
            )
        }

}
