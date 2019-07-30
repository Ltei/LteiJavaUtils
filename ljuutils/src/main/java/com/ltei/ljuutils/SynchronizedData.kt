package com.ltei.ljuutils

sealed class SynchronizedData<T: Any>(data: T) {
    var unsafeData = data
        protected set
    open val lock: Any get() = unsafeData
    inline fun <E> run(block: (T) -> E): E = synchronized(lock) { block(unsafeData) }
}

class SynchronizedMutableData<T: Any>(data: T): SynchronizedData<T>(data) {
    override val lock = Object()
    fun setData(data: T) { unsafeData = data }
}