package com.going.data.repositoryImpl

import com.going.data.datasource.TodoDataSource
import com.going.domain.entity.response.TodoModel
import com.going.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDataSource: TodoDataSource
) : TodoRepository {

    override suspend fun getTodoList(
        tripId: Long,
        category: String,
        process: String
    ): Result<List<TodoModel>> =
        runCatching {
            todoDataSource.getTodoListData(tripId, category, process).data.map { it.toTodoModel() }
        }

}