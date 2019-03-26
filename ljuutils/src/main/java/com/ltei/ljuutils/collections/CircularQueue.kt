package com.ltei.ljuutils.collections


class CircularQueue<T>(private val buffer: Array<T?>) : Collection<T> {

    companion object {
        inline fun <reified T> new(capacity: Int): CircularQueue<T> {
            return CircularQueue(Array<T?>(capacity) { null })
        }
    }

    private var mStartIdx = 0
    override var size = 0
        private set

    fun get(index: Int): T {
        if (index >= size) throw IndexOutOfBoundsException("$index >= $size")
        @Suppress("UNCHECKED_CAST") return buffer[(mStartIdx + index) % buffer.size] as T
    }

    fun push(element: T) {
        if (buffer.isNotEmpty()) {
            if (size == buffer.size) { // Pop then addFirst
                buffer[mStartIdx] = element
                mStartIdx = (mStartIdx + 1) % buffer.size
            } else {                    // Push
                buffer[mStartIdx + size] = element
                size++
            }
        }
    }

    fun pop(): T? {
        return if (size == 0) {
            null
        } else {
            @Suppress("UNCHECKED_CAST") val result = buffer[mStartIdx] as T
            buffer[mStartIdx] = null
            mStartIdx = (mStartIdx + 1) % buffer.size
            size--
            result
        }
    }

    override fun contains(element: T): Boolean {
        return buffer.any { element == it }
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.all { contains(it) }
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun iterator(): Iterator<T> {
        return CircularIterator()
    }


    private inner class CircularIterator : Iterator<T> {
        private var i = 0

        override fun hasNext(): Boolean {
            return i < size
        }

        override fun next(): T {
            if (i >= size) throw NoSuchElementException()
            @Suppress("UNCHECKED_CAST") val result = buffer[(mStartIdx + i) % buffer.size] as T
            i++
            return result
        }
    }

}