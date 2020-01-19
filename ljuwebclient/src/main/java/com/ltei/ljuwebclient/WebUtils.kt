package com.ltei.ljuwebclient

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


object WebUtils {
    val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    val MEDIA_TYPE_PNG = "image/png".toMediaType()
    val MEDIA_TYPE_JPEG = "image/jpeg".toMediaType()
    internal val EMPTY_REQUEST_BODY = ByteArray(0).toRequestBody(null)

    fun getFileSize(url: URL): Int {
        var conn: URLConnection? = null
        try {
            conn = url.openConnection()!!
            if (conn is HttpURLConnection) {
                conn.requestMethod = "HEAD"
            }
            conn.getInputStream()
            return conn.contentLength
        } catch (e: IOException) {
            throw RuntimeException(e)
        } finally {
            if (conn is HttpURLConnection)
                conn.disconnect()
        }
    }
}