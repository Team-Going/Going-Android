package com.going.presentation.util

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException

fun toErrorCode(throwable: Throwable): String = if (throwable is HttpException) {
    val jsonTemp = throwable.response()?.errorBody()?.byteString().toString()
    Log.e("TAG", "toErrorCode: $jsonTemp", )
    val json = jsonTemp.slice(6 until jsonTemp.length - 2) + "}"
    Log.e("TAG", "toErrorCode: $json", )
    JSONObject(json).getString("code")
} else {
    "NOT_HTTP"
}
