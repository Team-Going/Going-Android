package com.going.data.datasource

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TodoCreateRequestDto
import com.going.data.dto.response.MyTripInfoResponseDto
import com.going.data.dto.response.OurTripInfoResponseDto
import com.going.data.dto.response.TodoDetailResponseDto
import com.going.data.dto.response.TodoResponseDto

interface TodoDataSource {

    suspend fun getTodoListData(
        tripId: Long,
        category: String,
        progress: String
    ): BaseResponse<List<TodoResponseDto>>

    suspend fun postToCreateTodoData(
        tripId: Long,
        request: TodoCreateRequestDto
    ): NonDataBaseResponse

    suspend fun deleteTodoData(
        todoId: Long
    ): NonDataBaseResponse

    suspend fun getTodoDetailData(
        todoId: Long
    ): BaseResponse<TodoDetailResponseDto>

    suspend fun getMyTripInfo(
        tripId: Long
    ): BaseResponse<MyTripInfoResponseDto>

    suspend fun getOurTripInfo(
        tripId: Long
    ): BaseResponse<OurTripInfoResponseDto>

    suspend fun getToFinishTodoData(
        todoId: Long
    ): NonDataBaseResponse

    suspend fun getToRedoTodoData(
        todoId: Long
    ): NonDataBaseResponse

}
