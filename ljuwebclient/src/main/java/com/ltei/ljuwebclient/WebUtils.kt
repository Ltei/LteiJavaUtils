package com.ltei.ljuwebclient

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


object WebUtils {

    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val MEDIA_TYPE_PNG = "image/png".toMediaType()
    val MEDIA_TYPE_JPEG = "image/jpeg".toMediaType()
    val EMPTY_REQUEST_BODY = ByteArray(0).toRequestBody(null)

    fun jsonRequestBody(json: JsonElement): RequestBody = json.toString().toRequestBody(MEDIA_TYPE_JSON)
    fun jsonRequestBody(str: String): RequestBody = str.toRequestBody(MEDIA_TYPE_JSON)


    fun multipartImageBody(file: File, memberId: String = "file"): MultipartBody {
        val mediaType = if (file.endsWith("png")) {
            MEDIA_TYPE_PNG
        } else {
            MEDIA_TYPE_JPEG
        }

        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(memberId, file.name, file.asRequestBody(mediaType))
            .build()
    }

    fun buildGetUrl(root: String, json: JsonObject): String {
        return json.entrySet().let { entrySet ->
            entrySet.asSequence().withIndex().fold("${root.trimEnd('/')}?") { acc, it ->
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

}