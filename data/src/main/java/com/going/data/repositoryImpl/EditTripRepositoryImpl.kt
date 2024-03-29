package com.going.data.repositoryImpl

import com.going.data.datasource.EditTripDataSource
import com.going.data.dto.request.toEditTrioRequestDto
import com.going.domain.entity.request.EditTripRequestModel
import com.going.domain.entity.response.TripInfoModel
import com.going.domain.repository.EditTripRepository
import javax.inject.Inject

class EditTripRepositoryImpl @Inject constructor(
    private val editTripDataSource: EditTripDataSource,
) : EditTripRepository {
    override suspend fun getTripInfo(
        tripId: Long
    ): Result<TripInfoModel> =
        runCatching {
            editTripDataSource.getTripInfo(tripId).data.toTripInfoModel()
        }

    override suspend fun patchEditTripInfo(
        tripId: Long, request: EditTripRequestModel
    ): Result<Unit> =
        runCatching {
            editTripDataSource.patchEditTripInfo(
                tripId, request.toEditTrioRequestDto()
            )
        }

    override suspend fun patchQuitTrip(
        tripId: Long
    ): Result<Unit> =
        runCatching {
            editTripDataSource.patchQuitTrip(
                tripId
            )
        }
}
