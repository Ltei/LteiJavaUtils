package com.ltei.ljuwebclient

import java8.util.concurrent.CompletableFuture
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class FutureCompletionCallback(private val future: CompletableFuture<Response>) : Callback {

    override fun onFailure(call: Call, e: IOException) {
        future.completeExceptionally(e)
    }

    override fun onResponse(call: Call, response: Response) {
        future.complete(response)
    }
}