package com.ltei.ljubase

import java.io.Serializable

// We use casts instead of nonnull assertion to prevent issues with nullable type parameters

@Suppress("UNCHECKED_CAST")
class Result<V, E> private constructor(
        val isOk: Boolean,
        val okValue: V?,
        val errValue: E?
) : Cloneable, Serializable {

    val isErr: Boolean get() = !isOk

    fun unwrap(): V {
        if (isErr) throw IllegalStateException("Called unwrap on an err result")
        return okValue as V
    }

    fun unwrap(default: V): V = if (isOk) okValue as V else default
    inline fun unwrap(default: (E) -> V): V = if (isOk) okValue as V else default(errValue as E)

    fun unwrapErr(): E {
        if (isOk) throw IllegalStateException("Called unwrapErr on an ok result")
        return errValue as E
    }

    fun unwrapErr(default: E): E = if (isOk) default else errValue as E
    inline fun unwrapErr(default: (V) -> E): E = if (isErr) errValue as E else default(okValue as V)

    inline fun runOnOk(block: (V) -> Unit): Result<V, E> {
        if (isOk) block(okValue as V)
        return this
    }

    inline fun runOnErr(block: (E) -> Unit): Result<V, E> {
        if (isErr) block(errValue as E)
        return this
    }

    inline fun <U> map(block: (V) -> U): Result<U, E> = if (isOk) {
        Result.ok(block(okValue as V))
    } else {
        Result.err(errValue as E)
    }

    inline fun <U> tryMap(block: (V) -> Result<U, E>): Result<U, E> = if (isOk) {
        block(okValue as V)
    } else {
        Result.err(errValue as E)
    }

    inline fun <U> mapErr(block: (E) -> U): Result<V, U> = if (isOk) {
        Result.ok(okValue as V)
    } else {
        Result.err(block(errValue as E))
    }

    inline fun <U> tryMapErr(block: (E) -> Result<V, U>): Result<V, U> = if (isOk) {
        Result.ok(okValue as V)
    } else {
        block(errValue as E)
    }


    companion object {
        fun <T, E> ok(value: T): Result<T, E> = Result(true, value, null)
        fun <T, E> err(err: E): Result<T, E> = Result(false, null, err)

        inline fun <T> tryRun(block: () -> T): Result<T, Throwable> = try {
            Result.ok(block())
        } catch (t: Throwable) {
            Result.err(t)
        }
    }

}

// Result<T, T>

@Suppress("UNCHECKED_CAST")
fun <T> Result<T, T>.fusion(): T = if (isOk) {
    okValue as T
} else {
    errValue as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Result<T?, T>.fusionNullable(): T? = if (isOk) {
    okValue
} else {
    errValue as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Result<T, T?>.fusionNullableErr(): T? = if (isOk) {
    okValue as T
} else {
    errValue
}

// Result<T, Throwable>

@Suppress("UNCHECKED_CAST")
inline fun <V, U> Result<V, Throwable>.map(catch: Boolean, block: (V) -> U): Result<U, Throwable> {
    return if (isOk) {
        if (catch) {
            try {
                Result.ok<U, Throwable>(block(okValue as V))
            } catch (t: Throwable) {
                Result.err<U, Throwable>(t)
            }
        } else {
            Result.ok<U, Throwable>(block(okValue as V))
        }
    } else {
        Result.err<U, Throwable>(errValue!!)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <V, U> Result<V, Throwable>.tryMap(catch: Boolean, block: (V) -> Result<U, Throwable>): Result<U, Throwable> {
    return if (isOk) {
        if (catch) {
            try {
                block(okValue as V)
            } catch (t: Throwable) {
                Result.err<U, Throwable>(t)
            }
        } else {
            block(okValue as V)
        }
    } else {
        Result.err<U, Throwable>(errValue!!)
    }
}

// Performance tests

//fun main() {
//    val blocks = listOf(
//            Pair("Int", getTestBlocks(50)),
//            Pair("ListOfInt", getTestBlocks(listOf<Int>())),
//            Pair("DoubleArray", getTestBlocks(DoubleArray(50))),
//            Pair("objectArrayList", getTestBlocks(object : ArrayList<String>() {})),
//            Pair("Long", getTestBlocks(435354L)),
//            Pair("Double", getTestBlocks(5.0)),
//            Pair("String", getTestBlocks("Salut")),
//            Pair("Unit", getTestBlocks(Unit))
//    )
//
//    Bencher.multiBenchSplit(10000000, 1000000, blocks.flatMap {
//        listOf(
//                Pair("${it.first} cast", it.second.first),
//                Pair("${it.first} nonnull", it.second.second)
//        )
//    }).withIndex().fold(Pair(0L, 0L)) { acc, it ->
//        if (it.index % 2 == 0) {
//            Pair(acc.first + it.value, acc.second)
//        } else {
//            Pair(acc.first, acc.second + it.value)
//        }
//    }.let { (castResult, nonNullResult) ->
//        println("Cast result    = $castResult")
//        println("NonNull result = $nonNullResult")
//        println("Cast/NonNull   = ${castResult.toDouble()/nonNullResult.toDouble()}")
//    }
//
//    Bencher.multiBench(100000000/*, 1000000*/, blocks.flatMap {
//        listOf(
//                Pair("${it.first} cast", it.second.first),
//                Pair("${it.first} nonnull", it.second.second)
//        )
//    }).withIndex().fold(Pair(0L, 0L)) { acc, it ->
//        if (it.index % 2 == 0) {
//            Pair(acc.first + it.value, acc.second)
//        } else {
//            Pair(acc.first, acc.second + it.value)
//        }
//    }.let { (castResult, nonNullResult) ->
//        println("Cast result    = $castResult")
//        println("NonNull result = $nonNullResult")
//        println("Cast/NonNull   = ${castResult.toDouble()/nonNullResult.toDouble()}")
//    }
//}
//
//private inline fun <reified T> getTestBlocks(value: T?) : Pair<() -> Unit, () -> Unit> {
//    var v: T
//
//    val castBlock: () -> Unit = { v = value as T }
//    val nonNullBlock: () -> Unit = { v = value!! }
//
//    return Pair(castBlock, nonNullBlock)
//}