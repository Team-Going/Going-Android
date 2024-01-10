package com.going.domain.repository

import com.going.domain.entity.request.TodoRequestModel
import com.going.domain.entity.response.TodoModel

interface TodoRepository {

    suspend fun getTodoList(
        tripId: Long, request: TodoRequestModel
    ): Result<List<TodoModel>>

}