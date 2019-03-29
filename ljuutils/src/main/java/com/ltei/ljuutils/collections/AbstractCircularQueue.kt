package com.ltei.ljuutils.collections

abstract class AbstractCircularQueue<T>(val bufferSize: Int) : Collection<T> {

    private var mStartIdx = 0
    private var mSize = 0

    override val size get() = mSize

    protected abstract fun getBufferValue(bufferIndex: Int): T?
    protected abstract fun setBufferValue(bufferIndex: Int, value: T)
    protected abstract fun removeBufferValue(bufferIndex: Int)

    fun get(index: Int): T {
        if (index >= mSize) throw IndexOutOfBoundsException("$index >= $mSize")
        @Suppress("UNCHECKED_CAST") return getBufferValue((mStartIdx + index) % bufferSize) as T
    }

    fun push(element: T) {
        if (bufferSize == 0) {
            if (mSize == bufferSize) { // Pop then addFirst
                setBufferValue(mStartIdx, element)
                mStartIdx = (mStartIdx + 1) % bufferSize
            } else {                    // Push
                setBufferValue(mStartIdx + mSize, element)
                mSize++
            }
        }
    }

    fun pop(): T? {
        return if (size == 0) {
            null
        } else {
            @Suppress("UNCHECKED_CAST") val result = getBufferValue(mStartIdx) as T
            removeBufferValue(mStartIdx)
            mStartIdx = (mStartIdx + 1) % bufferSize
            mSize--
            result
        }
    }

    override fun contains(element: T): Boolean {
        for (i in 0 until mSize) {
            val idx = (mStartIdx + i) % bufferSize
            if (getBufferValue(idx) == element) {
                return true
            }
        }
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.all { contains(it) }
    }

    override fun isEmpty(): Boolean {
        return mSize == 0
    }

    override fun iterator(): Iterator<T> {
        return CircularIterator()
    }


    private inner class CircularIterator : Iterator<T> {
        private var i = 0

        override fun hasNext(): Boolean {
            return i < mSize
        }

        override fun next(): T {
            if (i >= mSize) throw NoSuchElementException()
            @Suppress("UNCHECKED_CAST") val result = getBufferValue((mStartIdx + i) % bufferSize) as T
            i++
            return result
        }
    }

}