package com.going.domain.repository

import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.entity.response.MyTripInfoModel
import com.going.domain.entity.response.TodoDetailModel
import com.going.domain.entity.response.TodoModel

interface TodoRepository {

    suspend fun getTodoList(
        tripId: Long,
        category: String,
        progress: String
    ): Result<List<TodoModel>>

    suspend fun postToCreateTodo(
        tripId: Long,
        request: TodoCreateRequestModel
    ): Result<Unit>

    suspend fun deleteTodo(
        todoId: Long
    ): Result<Unit>

    suspend fun getTodoDetail(
        todoId: Long
    ): Result<TodoDetailModel>

    suspend fun getMyTripInfo(
        todoId: Long
    ): Result<MyTripInfoModel>

}