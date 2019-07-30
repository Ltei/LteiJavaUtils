package com.ltei.ljuwebclient

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


object WebUtils {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val MEDIA_TYPE_PNG = "image/png".toMediaType()
    val MEDIA_TYPE_JPEG = "image/jpeg".toMediaType()
    val EMPTY_REQUEST_BODY = RequestBody.create(null, ByteArray(0))

    fun jsonRequestBody(json: JsonElement): RequestBody = RequestBody.create(MEDIA_TYPE_JSON, json.toString())
    fun jsonRequestBody(str: String): RequestBody = RequestBody.create(MEDIA_TYPE_JSON, str)


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
        val root = root.trimEnd('/')
        return json.entrySet().let { entrySet ->
            entrySet.asSequence().withIndex().fold("$root?") { acc, it ->
                val valueString = try {
                    it.value.value.asString
                } catch (t: Throwable) {
                    it.value.value.toString()
                }
                val suffix = if (it.index < entrySet.count() - 1) "&" else ""
                "$acc${it.value.key}=$valueString$suffix"
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