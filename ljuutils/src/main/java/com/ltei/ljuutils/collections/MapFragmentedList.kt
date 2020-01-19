package com.ltei.ljuutils.collections

class MapFragmentedList<T>(override val size: Int) : AbstractList<T?>() {
    private val map = mutableMapOf<Int, T>()

    operator fun set(index: Int, element: T?): T? {
        if (index !in 0 until size)
            throw IllegalArgumentException(index.toString())
        if (element == null) map.remove(index) else map[index] = element
        return element
    }

    override operator fun get(index: Int) = map[index]
}