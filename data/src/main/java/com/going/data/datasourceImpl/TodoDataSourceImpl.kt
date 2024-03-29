package com.going.data.datasourceImpl

import com.going.data.datasource.TodoDataSource
import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TodoChangeRequestDto
import com.going.data.dto.request.TodoCreateRequestDto
import com.going.data.dto.response.CheckFriendsResponseDto
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
        tripId: Long,
        todoId: Long
    ): BaseResponse<TodoDetailResponseDto> =
        todoService.getTodoDetail(tripId, todoId)

    override suspend fun getMyTripInfo(
        tripId: Long
    ): BaseResponse<MyTripInfoResponseDto> =
        todoService.getMyTripInfo(tripId)

    override suspend fun getOurTripInfo(
        tripId: Long
    ): BaseResponse<OurTripInfoResponseDto> =
        todoService.getOurTripInfo(tripId)

    override suspend fun getToFinishTodoData(
        todoId: Long
    ): NonDataBaseResponse =
        todoService.getToFinishTodo(todoId)

    override suspend fun getToRedoTodoData(
        todoId: Long
    ): NonDataBaseResponse =
        todoService.getToRedoTodo(todoId)

    override suspend fun getFriendsList(
        tripId: Long
    ): BaseResponse<CheckFriendsResponseDto> =
        todoService.getFriendsList(tripId)

    override suspend fun patchTodoData(
        tripId: Long,
        todoId: Long,
        request: TodoChangeRequestDto
    ): NonDataBaseResponse =
        todoService.patchTodo(tripId, todoId, request)

}
