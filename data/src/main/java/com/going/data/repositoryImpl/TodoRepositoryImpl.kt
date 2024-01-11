package com.going.data.repositoryImpl

import com.going.data.datasource.TodoDataSource
import com.going.data.dto.request.toTodoCreateRequestDto
import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.entity.response.TodoModel
import com.going.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDataSource: TodoDataSource
) : TodoRepository {

    override suspend fun getTodoList(
        tripId: Long,
        category: String,
        progress: String
    ): Result<List<TodoModel>> =
        runCatching {
            todoDataSource.getTodoListData(tripId, category, progress).data.map { it.toTodoModel() }
        }

    override suspend fun postToCreateTodo(
        tripId: Long,
        request: TodoCreateRequestModel
    ): Result<Unit> =
        runCatching {
            todoDataSource.postToCreateTodoData(tripId, request.toTodoCreateRequestDto())
        }

    override suspend fun deleteTodo(
        todoId: Long
    ): Result<Unit> =
        runCatching {
            todoDataSource.deleteTodoData(todoId)
        }

}