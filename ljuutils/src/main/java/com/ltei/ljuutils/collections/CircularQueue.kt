package com.ltei.ljuutils.collections


class CircularQueue<T>(private val buffer: Array<T?>) : AbstractCircularQueue<T>(buffer.size) {

    companion object {
        inline fun <reified T> new(capacity: Int): CircularQueue<T> {
            return CircularQueue(Array<T?>(capacity) { null })
        }
    }

    override fun getBufferValue(bufferIndex: Int): T? = buffer[bufferIndex]

    override fun setBufferValue(bufferIndex: Int, value: T) {
        buffer[bufferIndex] = value
    }

    override fun removeBufferValue(bufferIndex: Int) {
        buffer[bufferIndex] = null
    }
}