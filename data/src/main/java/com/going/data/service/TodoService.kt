package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.response.TodoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {

    @GET("api/trips/{tripId}/todos")
    suspend fun getTodoList(
        @Path("tripId") tripId: Long,
        @Query("category") category: String,
        @Query("process") process: String
    ): BaseResponse<List<TodoResponseDto>>

}