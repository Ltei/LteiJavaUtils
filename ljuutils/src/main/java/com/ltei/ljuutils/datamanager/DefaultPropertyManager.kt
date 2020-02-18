package com.ltei.ljuutils.datamanager

open class DefaultPropertyManager<T> : PropertyManager<T>() {
    override fun isEmptyImpl(data: T) = false
    override fun normalizeImpl(data: T): T? = data
    override fun mergeImpl(previous: T, new: T): T? = previous
    override fun areSameDataImpl(a: T, b: T) = (a == b)
}