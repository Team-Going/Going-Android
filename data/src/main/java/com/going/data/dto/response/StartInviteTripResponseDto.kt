package com.going.data.dto.response

import com.going.domain.entity.response.StartInviteTripModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StartInviteTripResponseDto (
    @SerialName("tripId")
    val tripId: Long,
){
    fun toStartInviteTripModel() =
        StartInviteTripModel(tripId)
}
