package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.TodoResponseDto

interface TodoDataSource {

    suspend fun getTodoListData(
        tripId: Long,
        category: String,
        process: String
    ): BaseResponse<List<TodoResponseDto>>

}