package com.ltei.ljuutils.collections

class IntCircularQueue(private val buffer: IntArray) : AbstractCircularQueue<Int>(buffer.size) {

    constructor(bufferSize: Int) : this(IntArray(bufferSize))

    override fun getBufferValue(bufferIndex: Int): Int? = buffer[bufferIndex]

    override fun setBufferValue(bufferIndex: Int, value: Int) {
        buffer[bufferIndex] = value
    }

    override fun removeBufferValue(bufferIndex: Int) {}
}

class ShortCircularQueue(private val buffer: ShortArray) : AbstractCircularQueue<Short>(buffer.size) {

    constructor(bufferSize: Int) : this(ShortArray(bufferSize))

    override fun getBufferValue(bufferIndex: Int): Short? = buffer[bufferIndex]

    override fun setBufferValue(bufferIndex: Int, value: Short) {
        buffer[bufferIndex] = value
    }

    override fun removeBufferValue(bufferIndex: Int) {}
}

class ByteCircularQueue(private val buffer: ByteArray) : AbstractCircularQueue<Byte>(buffer.size) {

    constructor(bufferSize: Int) : this(ByteArray(bufferSize))

    override fun getBufferValue(bufferIndex: Int): Byte? = buffer[bufferIndex]

    override fun setBufferValue(bufferIndex: Int, value: Byte) {
        buffer[bufferIndex] = value
    }

    override fun removeBufferValue(bufferIndex: Int) {}
}

class FloatCircularQueue(private val buffer: FloatArray) : AbstractCircularQueue<Float>(buffer.size) {

    constructor(bufferSize: Int) : this(FloatArray(bufferSize))

    override fun getBufferValue(bufferIndex: Int): Float? = buffer[bufferIndex]

    override fun setBufferValue(bufferIndex: Int, value: Float) {
        buffer[bufferIndex] = value
    }

    override fun removeBufferValue(bufferIndex: Int) {}
}

class DoubleCircularQueue(private val buffer: DoubleArray) : AbstractCircularQueue<Double>(buffer.size) {

    constructor(bufferSize: Int) : this(DoubleArray(bufferSize))

    override fun getBufferValue(bufferIndex: Int): Double? = buffer[bufferIndex]

    override fun setBufferValue(bufferIndex: Int, value: Double) {
        buffer[bufferIndex] = value
    }

    override fun removeBufferValue(bufferIndex: Int) {}
}

