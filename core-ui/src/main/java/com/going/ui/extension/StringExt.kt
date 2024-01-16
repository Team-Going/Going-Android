package com.going.ui.extension

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import java.text.BreakIterator

fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")

fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")

fun String.getGraphemeLength(): Int {
    val breakIterator: BreakIterator = BreakIterator.getCharacterInstance()

    breakIterator.setText(this)

    var count = 0
    while (breakIterator.next() != BreakIterator.DONE) {
        count++
    }

    return count
}

fun String.setBulletPoint(): SpannableString {
    val string = SpannableString(this)
    string.setSpan(BulletSpan(10), 0, this.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    return string
}
