package com.going.data.datasourceImpl

import com.going.data.datasource.TodoDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.response.TodoResponseDto
import com.going.data.service.TodoService
import javax.inject.Inject

class TodoDataSourceImpl @Inject constructor(
    private val todoService: TodoService
) : TodoDataSource {

    override suspend fun getTodoListData(
        tripId: Long,
        category: String,
        progress: String
    ): BaseResponse<List<TodoResponseDto>> =
        todoService.getTodoList(tripId, category, progress)

}