package com.ltei.ljuwebclient

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.ltei.ljubase.LException
import com.ltei.ljubase.LLog
import com.ltei.ljubase.Result
import com.ltei.ljubase.tryMap
import java8.util.concurrent.CompletableFuture
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class WebClient {

    private val okHttpClient = OkHttpClient.Builder()
            .cookieJar(SimpleCookieJar())
            .build()

    fun request(request: Request.Builder): CompletableFuture<Result<Response, Throwable>> {
        val built = request.build()
        LLog.debug(javaClass, "Request : ${built.method()} ${built.url()}")
        val future = CompletableFuture<Result<Response, Throwable>>()
        okHttpClient.newCall(built).enqueue(FutureCompletionCallback(future))
        return future
    }

    fun requestJson(request: Request.Builder): CompletableFuture<Result<JsonElement, Throwable>> {
        return request(request).thenApply { result ->
            result.tryMap(true) {
                LLog.debug(javaClass, "onResponse")
                val body = it.body()!!.string()
                LLog.debug(javaClass, "> Got body : $body")
                val json = JsonParser().parse(body)
                if (it.isSuccessful) {
                    LLog.debug(javaClass, ">> Got data : $json")
                    Result.ok<JsonElement, Throwable>(json)
                } else {
                    LLog.debug(javaClass, ">> Got error : $json")
                    Result.err<JsonElement, Throwable>(LException(json.toString()))
                }
            }
        }
    }

}