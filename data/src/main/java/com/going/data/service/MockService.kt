package com.going.data.service

import com.going.data.dto.response.MockFollowerResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MockService {

    @GET("api/users")
    suspend fun getFollowerList(
        @Query("page") page: Int
    ): MockFollowerResponseDto

}