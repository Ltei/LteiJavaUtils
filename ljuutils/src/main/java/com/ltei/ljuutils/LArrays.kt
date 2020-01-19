package com.ltei.ljuutils

@Deprecated("Moved to misc.ArrayUtils")
object LArrays {

    inline fun <T, E> tryMap(iterable: Iterable<T>, block: (T) -> E?): List<E>? {
        return iterable.map {
            block(it) ?: return null
        }
    }

}