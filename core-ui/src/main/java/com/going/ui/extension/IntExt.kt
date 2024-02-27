package com.going.ui.extension

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.TypedValue

fun Int.dpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}
