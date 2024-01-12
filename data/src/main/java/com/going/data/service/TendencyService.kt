package com.going.data.service

import com.going.data.dto.NonDataBaseResponse
import com.going.data.dto.request.TendencyTestRequestDto
import retrofit2.http.Body
import retrofit2.http.PATCH

interface TendencyService {
    @PATCH("api/users/test")
    suspend fun patchTendencyTest(
        @Body result: TendencyTestRequestDto,
    ): NonDataBaseResponse<Any?>
}
