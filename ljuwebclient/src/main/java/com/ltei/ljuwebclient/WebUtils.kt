package com.ltei.ljuwebclient

import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


object WebUtils {

    val MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8")
    val MEDIA_TYPE_PNG = MediaType.parse("image/png")
    val MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg")
    val EMPTY_REQUEST_BODY = RequestBody.create(null, ByteArray(0))

    fun multipartImageBody(file: File, memberId: String = "file"): MultipartBody {
        val mediaType = if (file.endsWith("png")) {
            MEDIA_TYPE_PNG
        } else {
            MEDIA_TYPE_JPEG
        }

        return MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(memberId, file.name, RequestBody.create(mediaType, file))
                .build()
    }

    fun buildGetUrl(root: String, json: JsonObject): String {
        return json.entrySet().let { entrySet ->
            entrySet.asSequence().withIndex().fold("$root?") { acc, it ->
                if (it.index < entrySet.count() - 1) {
                    "$acc${it.value.key}=${it.value.value}&"
                } else {
                    "$acc${it.value.key}=${it.value.value}"
                }
            }
        }
    }

    fun pageInfoJson(offset: Int, count: Int): JsonObject {
        val result = JsonObject()
        result.addProperty("offset", offset)
        result.addProperty("count", count)
        return result
    }

}