package com.ltei.ljuutils.properties

interface Property<T> {
    val value: T
    fun listen(listener: (T) -> Unit)

    companion object {
        fun <T> readOnly(value: T): Property<T> = MutableProperty(value)
    }
}