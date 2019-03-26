package com.ltei.ljuwebclient

import com.ltei.ljubase.Result
import java8.util.concurrent.CompletableFuture

object FutureUtils {

    fun <T, E> fold(futures: List<CompletableFuture<Result<T, E>>>)
            : CompletableFuture<Result<List<T>, E>> {

        return CompletableFuture.allOf(*futures.toTypedArray()).thenApply { _ ->
            val results = futures.map { it.join() }
            val err = results.find { it.isErr }
            if (err == null) {
                Result.ok<List<T>, E>(results.map { it.unwrap() })
            } else {
                Result.err<List<T>, E>(err.unwrapErr())
            }
        }

    }

    fun <T, E> foldAll(futures: List<CompletableFuture<Result<List<T>, E>>>)
            : CompletableFuture<Result<List<T>, E>> {

        return CompletableFuture.allOf(*futures.toTypedArray()).thenApply { _ ->
            val results = futures.map { it.join() }
            val err = results.find { it.isErr }
            if (err == null) {
                val list = results.fold(mutableListOf<T>()) { acc, it ->
                    acc.addAll(it.unwrap())
                    acc
                }
                Result.ok<List<T>, E>(list)
            } else {
                Result.err<List<T>, E>(err.unwrapErr())
            }
        }

    }

}

//fun <Value, Error, Obj : Iterable<CompletableFuture<Result<Value, Error>>>> Obj.fold():
//        CompletableFuture<Result<List<Value>, Error>> {
//
//    return CompletableFuture.allOf(*this.toList().toTypedArray()).thenApply { _ ->
//        val results = this.map { it.join() }
//        val err = results.find { it.isErr }
//        if (err == null) {
//            Result.ok<List<Value>, Error>(results.map { it.unwrap() })
//        } else {
//            Result.err<List<Value>, Error>(err.unwrapErr())
//        }
//    }
//}
//
//fun <Value, Error, InnerObj : Iterable<Value>, Obj : Iterable<CompletableFuture<Result<InnerObj, Error>>>> Obj.foldAll():
//        CompletableFuture<Result<List<Value>, Error>> {
//
//    return CompletableFuture.allOf(*this.toList().toTypedArray()).thenApply { _ ->
//        val results = this.map { it.join() }
//        val err = results.find { it.isErr }
//        if (err == null) {
//            val list = results.fold(mutableListOf<Value>()) { acc, it ->
//                acc.addAll(it.unwrap())
//                acc
//            }
//            Result.ok<List<Value>, Error>(list)
//        } else {
//            Result.err<List<Value>, Error>(err.unwrapErr())
//        }
//    }
//}