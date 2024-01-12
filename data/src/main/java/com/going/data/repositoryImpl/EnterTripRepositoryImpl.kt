package com.going.data.repositoryImpl

import com.going.data.datasource.EnterTripDataSource
import com.going.data.dto.request.toEnterTripRequestDto
import com.going.domain.entity.request.RequestEnterTripModel
import com.going.domain.entity.response.EnterTripModel
import com.going.domain.repository.EnterTripRepository
import javax.inject.Inject

class EnterTripRepositoryImpl @Inject constructor(
    private val enterTripDataSource: EnterTripDataSource,
) : EnterTripRepository {

    override suspend fun postEnterTrip(
        requestEnterTripModel: RequestEnterTripModel
    ): Result<EnterTripModel?> =
        runCatching {
            enterTripDataSource.postEnterTrip(
                requestEnterTripModel.toEnterTripRequestDto(),
            ).data?.toEnterTripModel()
        }
}
