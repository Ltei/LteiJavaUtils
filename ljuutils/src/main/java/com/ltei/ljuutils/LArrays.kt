package com.ltei.ljuutils

object LArrays {

    inline fun <T, E> tryMap(iterable: Iterable<T>, block: (T) -> E?): List<E>? {
        return iterable.map {
            block(it) ?: return null
        }
    }

}