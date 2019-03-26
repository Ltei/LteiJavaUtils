package com.ltei.ljuwebclient

import com.ltei.ljubase.Result
import java8.util.concurrent.CompletableFuture
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class FutureCompletionCallback(private val future: CompletableFuture<Result<Response, Throwable>>) :
        Callback {

    override fun onFailure(call: Call, e: IOException) {
        future.complete(Result.err(e))
    }

    override fun onResponse(call: Call, response: Response) {
        future.complete(Result.ok(response))
    }
}