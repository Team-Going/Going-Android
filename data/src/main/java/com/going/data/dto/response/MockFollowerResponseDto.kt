package com.going.data.dto.response

import com.going.domain.entity.response.MockFollowerModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MockFollowerResponseDto(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<User>,
    @SerialName("support")
    val support: Support,
) {
    @Serializable
    data class User(
        @SerialName("id")
        val id: Int,
        @SerialName("email")
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        @SerialName("avatar")
        val avatar: String
    )
    @Serializable
    data class Support(
        @SerialName("url")
        val url: String,
        @SerialName("text")
        val text: String
    )

    fun toMockFollowerModel() : List<MockFollowerModel> = data.map { user ->
        MockFollowerModel(
            id = user.id,
            name = "${user.firstName} ${user.lastName}",
            email = user.email,
            image = user.avatar
        )
    }
}