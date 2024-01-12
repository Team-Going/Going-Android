package com.going.domain.entity

data class ProfileMock(
    val resultImage: Int,
    val profileTitle: String,
    val profileSubTitle: String,
    val tags: List<String>,
    val profileBoxInfo: List<BoxInfo>,
) {
    data class BoxInfo(
        val title: String,
        val first: String,
        val second: String,
        val third: String,
    )
}
