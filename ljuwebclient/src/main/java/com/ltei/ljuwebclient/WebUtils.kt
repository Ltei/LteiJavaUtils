package com.ltei.ljuwebclient

import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

fun Request.Builder.post() = post(WebUtils.EMPTY_REQUEST_BODY)
fun Request.Builder.postJson(json: String) = post(json.toRequestBody(WebUtils.MEDIA_TYPE_JSON))
fun Request.Builder.patch() = patch(WebUtils.EMPTY_REQUEST_BODY)
fun Request.Builder.patchJson(json: String) = post(json.toRequestBody(WebUtils.MEDIA_TYPE_JSON))

fun Request.Builder.url(url: String, params: JsonObject): Request.Builder {
    val fullUrl = params.entrySet().let { entrySet ->
        entrySet.asSequence().withIndex().fold("${url.trimEnd('/')}?") { acc, it ->
            val valueString = try {
                it.value.value.asString
            } catch (t: Throwable) {
                it.value.value.toString()
            }
            val suffix = if (it.index < entrySet.count() - 1) "&" else ""
            "$acc${it.value.key}=$valueString$suffix"
        }
    }
    return url(fullUrl)
}

fun Request.Builder.url(url: String, params: Map<String, String>): Request.Builder {
    val fullUrl = params.asSequence().withIndex().fold("${url.trimEnd('/')}?") { acc, it ->
        val valueString = try {
            it.value.value
        } catch (t: Throwable) {
            it.value.value
        }
        val suffix = if (it.index < params.size - 1) "&" else ""
        "$acc${it.value.key}=$valueString$suffix"
    }
    return url(fullUrl)
}

object WebUtils {
    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val MEDIA_TYPE_PNG = "image/png".toMediaType()
    val MEDIA_TYPE_JPEG = "image/jpeg".toMediaType()
    internal val EMPTY_REQUEST_BODY = ByteArray(0).toRequestBody(null)
}