package com.going.data.service

import com.going.data.dto.BaseResponse
import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TodoChangeRequestDto
import com.going.data.dto.request.TodoCreateRequestDto
import com.going.data.dto.response.CheckFriendsResponseDto
import com.going.data.dto.response.MyTripInfoResponseDto
import com.going.data.dto.response.OurTripInfoResponseDto
import com.going.data.dto.response.TodoDetailResponseDto
import com.going.data.dto.response.TodoResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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
    ): NonDataBaseResponse

    @DELETE("api/trips/todos/{todoId}")
    suspend fun deleteTodo(
        @Path("todoId") todoId: Long
    ): NonDataBaseResponse

    @GET("api/trips/todos/{todoId}")
    suspend fun getTodoDetail(
        @Path("todoId") todoId: Long
    ): BaseResponse<TodoDetailResponseDto>

    @GET("/api/trips/{tripId}/my")
    suspend fun getMyTripInfo(
        @Path("tripId") tripId: Long
    ): BaseResponse<MyTripInfoResponseDto>

    @GET("/api/trips/{tripId}/our")
    suspend fun getOurTripInfo(
        @Path("tripId") tripId: Long
    ): BaseResponse<OurTripInfoResponseDto>

    @GET("api/trips/todos/{todoId}/complete")
    suspend fun getToFinishTodo(
        @Path("todoId") todoId: Long
    ): NonDataBaseResponse

    @GET("api/trips/todos/{todoId}/incomplete")
    suspend fun getToRedoTodo(
        @Path("todoId") todoId: Long
    ): NonDataBaseResponse

    @GET("api/trips/{tripId}/participants")
    suspend fun getFriendsList(
        @Path("tripId") tripId: Long
    ): BaseResponse<CheckFriendsResponseDto>

    @PATCH("/api/trips/todos/{todoId}")
    suspend fun patchTodo(
        @Path("todoId") todoId: Long,
        @Body request: TodoChangeRequestDto
    ): NonDataBaseResponse

}
