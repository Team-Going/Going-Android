package com.going.domain.repository

import com.going.domain.entity.request.TodoChangeRequestModel
import com.going.domain.entity.request.TodoCreateRequestModel
import com.going.domain.entity.response.CheckFriendsModel
import com.going.domain.entity.response.MyTripInfoModel
import com.going.domain.entity.response.OurTripInfoModel
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
        tripId: Long,
        todoId: Long
    ): Result<TodoDetailModel>

    suspend fun getMyTripInfo(
        tripId: Long
    ): Result<MyTripInfoModel>

    suspend fun getOurTripInfo(
        tripId: Long
    ): Result<OurTripInfoModel>

    suspend fun getToFinishTodo(
        todoId: Long
    ): Result<Unit>

    suspend fun getToRedoTodo(
        todoId: Long
    ): Result<Unit>

    suspend fun getFriendsList(
        tripId: Long
    ): Result<CheckFriendsModel>

    suspend fun patchTodo(
        tripId: Long,
        todoId: Long,
        request: TodoChangeRequestModel
    ): Result<Unit>
}