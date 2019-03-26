package com.ltei.ljuutils.matrix

class IntMatrix(override val rows: Int, override val cols: Int) : IArrayMatrix<Int> {
    val array = IntArray(rows * cols)
    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Int) {
        array[index] = value
    }
}

class ShortMatrix(override val rows: Int, override val cols: Int) : IArrayMatrix<Short> {
    val array = ShortArray(rows * cols)
    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Short) {
        array[index] = value
    }
}

class FloatMatrix(override val rows: Int, override val cols: Int) : IArrayMatrix<Float> {
    val array = FloatArray(rows * cols)
    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Float) {
        array[index] = value
    }
}

class DoubleMatrix(override val rows: Int, override val cols: Int) : IArrayMatrix<Double> {
    val array = DoubleArray(rows * cols)
    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Double) {
        array[index] = value
    }
}

class BooleanMatrix(override val rows: Int, override val cols: Int) : IArrayMatrix<Boolean> {
    val array = BooleanArray(rows * cols)
    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Boolean) {
        array[index] = value
    }
}