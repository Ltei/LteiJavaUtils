package com.ltei.ljuutils.datamanager

abstract class PropertyManager<T> {
    fun isEmpty(data: T?): Boolean {
        return data?.let { isEmptyImpl(it) } ?: true
    }

    fun normalize(data: T?): T? {
        return if (isEmpty(data)) null else data
    }

    fun merge(previous: T?, new: T?): T? {
        if (isEmpty(previous)) return new
        if (isEmpty(new)) return previous
        return normalize(mergeImpl(previous!!, new!!))
    }
    fun areSameData(a: T?, b: T?): Boolean {
        if (a == null || b == null) return false
        return areSameDataImpl(a, b)
    }

    fun areSameDataOrEmpty(a: T?, b: T?): Boolean {
        if (isEmpty(a) || isEmpty(b)) return true
        return areSameDataImpl(a!!, b!!)
    }

    protected abstract fun isEmptyImpl(data: T): Boolean
    protected abstract fun normalizeImpl(data: T): T?
    protected abstract fun mergeImpl(previous: T, new: T): T?
    protected abstract fun areSameDataImpl(a: T, b: T): Boolean
}