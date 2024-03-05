package com.going.data.datasourceImpl

import com.going.data.datasource.EditTripDataSource
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.EditTripInfoRequestDto
import com.going.data.service.EditTripService
import javax.inject.Inject

class EditTripDataSourceImpl @Inject constructor(
    private val editTripService: EditTripService,
) : EditTripDataSource{

    override suspend fun patchEditTripInfo(
        tripId: Long,
        request: EditTripInfoRequestDto
    ): NonDataBaseResponse = editTripService.patchEditTripInfo(
        tripId, request)
}