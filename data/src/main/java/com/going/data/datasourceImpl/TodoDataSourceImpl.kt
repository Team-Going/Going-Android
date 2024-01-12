package com.going.data.datasourceImpl

import com.going.data.datasource.TodoDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TodoCreateRequestDto
import com.going.data.dto.response.MyTripInfoResponseDto
import com.going.data.dto.response.OurTripInfoResponseDto
import com.going.data.dto.response.TodoDetailResponseDto
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

    override suspend fun postToCreateTodoData(
        tripId: Long,
        request: TodoCreateRequestDto
    ): NonDataBaseResponse =
        todoService.postToCreateTodo(tripId, request)

    override suspend fun deleteTodoData(
        todoId: Long
    ): NonDataBaseResponse =
        todoService.deleteTodo(todoId)

    override suspend fun getTodoDetailData(
        todoId: Long
    ): BaseResponse<TodoDetailResponseDto> =
        todoService.getTodoDetail(todoId)

    override suspend fun getMyTripInfo(
        tripId: Long
    ): BaseResponse<MyTripInfoResponseDto> =
        todoService.getMyTripInfo(tripId)

    override suspend fun getOurTripInfo(
        tripId: Long
    ): BaseResponse<OurTripInfoResponseDto> =
        todoService.getOurTripInfo(tripId)

}
