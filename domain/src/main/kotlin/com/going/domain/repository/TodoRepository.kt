package com.going.domain.repository

import com.going.domain.entity.response.TodoModel

interface TodoRepository {

    suspend fun getTodoList(
        tripId: Long,
        category: String,
        process: String
    ): Result<List<TodoModel>>

}