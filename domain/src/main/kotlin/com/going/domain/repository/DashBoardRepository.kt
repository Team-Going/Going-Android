package com.going.domain.repository

import com.going.domain.entity.response.DashBoardModel

interface DashBoardRepository {
    suspend fun getDashBoardList(
        name : String,
    ) : Result<List<DashBoardModel>>
}