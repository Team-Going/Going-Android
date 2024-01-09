package com.going.presentation.util

import org.json.JSONObject
import retrofit2.HttpException

fun toErrorCode(throwable: Throwable): String {
    lateinit var errorCode: String

    if (throwable is HttpException) {
        val jsonTemp = throwable.response()?.errorBody()?.byteString().toString()
        val json = jsonTemp.slice(6 until jsonTemp.length)
        errorCode = JSONObject(json).getString("code")
    } else {
        errorCode = "EXCEPTION"
    }

    return errorCode
}
