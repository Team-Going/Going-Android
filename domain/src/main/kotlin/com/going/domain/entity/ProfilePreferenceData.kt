package com.going.domain.entity

data class ProfilePreferenceData(
    val number: String,
    val question: String,
    val rightPrefer: String,
    val leftPrefer: String,
    val preferenceIndex: Int
)
