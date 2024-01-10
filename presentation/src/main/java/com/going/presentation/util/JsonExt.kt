package com.going.presentation.util

import retrofit2.HttpException

fun toErrorCode(throwable: Throwable): String = if (throwable is HttpException) {
    val jsonTemp = throwable.response()?.errorBody()?.byteString().toString()
    val codeIndex = jsonTemp.indexOf("code")
    var errorCode = jsonTemp.slice(codeIndex + 7..codeIndex + 12)

    if (errorCode.endsWith('\"')) errorCode = errorCode.slice(0 until errorCode.length - 1)

    errorCode
} else {
    "NOT_HTTP"
}
