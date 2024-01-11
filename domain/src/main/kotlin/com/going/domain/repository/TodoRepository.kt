package com.going.domain.repository

import com.going.domain.entity.request.TodoCreateRequestModel
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

}