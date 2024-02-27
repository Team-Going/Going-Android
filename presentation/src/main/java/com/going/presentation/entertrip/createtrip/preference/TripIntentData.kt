package com.going.presentation.entertrip.createtrip.preference

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TripIntentData(
    val name: String,
    val startYear: Int,
    val startMonth: Int,
    val startDay: Int,
    val endYear: Int,
    val endMonth: Int,
    val endDay: Int
) : Parcelable

