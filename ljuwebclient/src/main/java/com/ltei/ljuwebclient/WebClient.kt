package com.ltei.ljuwebclient

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.ltei.ljubase.debug.Logger
import java8.util.concurrent.CompletableFuture
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class WebClient {

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(SimpleCookieJar())
        .build()

    fun request(request: Request.Builder): CompletableFuture<Response> {
        val built = request.build()
        logger.debug("Request : ${built.method} ${built.url}")
        val future = CompletableFuture<Response>()
        okHttpClient.newCall(built).enqueue(FutureCompletionCallback(future))
        return future.thenApply {

            if (!it.isSuccessful) {
                val body = it.body?.string()
                logger.debug(">> Got error : [${it.code}] $body")
                throw Exception(">> Got error : [${it.code}] $body")
            } else {
                logger.debug(">> Got data : [${it.code}]")
            }
            it
        }
    }

    fun requestJson(request: Request.Builder): CompletableFuture<JsonElement> {
        return request(request).thenApply {
            val body = it.body!!.string()
            logger.debug(">> Got body : $body")
            val json = JsonParser().parse(body)
            json
        }
    }

    companion object {
        val logger = Logger(WebClient::class.java)
    }

}