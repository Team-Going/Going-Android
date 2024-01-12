package com.going.data.repositoryImpl

import com.going.data.datasource.AuthDataSource
import com.going.data.datasource.ProfileDataSource
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.repository.AuthRepository
import com.going.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getUserProfile(): Result<UserProfileRequestModel> = runCatching {
        profileDataSource.getUserProfile().data.toProfileModel()
    }
}
