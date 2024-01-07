package com.going.domain.entity

data class TendencyResultMock(
    val tendencyTitle: String,
    val tendencySubTitle: String,
    val tags: List<String>,
    val tendencyBoxInfo: List<BoxInfo>,
) {
    data class BoxInfo(
        val title: String,
        val first: String,
        val second: String,
        val third: String,
    )
}
