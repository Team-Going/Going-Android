package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.NullableBaseResponse
import com.going.data.dto.request.TodoCreateRequestDto
import com.going.data.dto.response.TodoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {

    @GET("api/trips/{tripId}/todos")
    suspend fun getTodoList(
        @Path("tripId") tripId: Long,
        @Query("category") category: String,
        @Query("progress") progress: String
    ): BaseResponse<List<TodoResponseDto>>

    @POST("api/trips/{tripId}/todos")
    suspend fun postToCreateTodo(
        @Path("tripId") tripId: Long,
        @Body request: TodoCreateRequestDto
    ): NullableBaseResponse<String>

}