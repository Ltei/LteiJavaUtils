package com.ltei.ljuutils.matrix

class IntMatrix private constructor(
    override val rows: Int,
    override val cols: Int,
    val array: IntArray
) : IArrayMatrix<Int> {

    constructor(rows: Int, cols: Int) : this(rows, cols, IntArray(rows * cols))
    constructor(rows: Int, cols: Int, init: Int) : this(rows, cols, IntArray(rows * cols) { init })
    constructor(rows: Int, cols: Int, init: (row: Int, col: Int) -> Int) :
            this(rows, cols, IntArray(rows * cols) {
                init(IArrayMatrix.getRowFromArrayIndex(rows, it), IArrayMatrix.getColFromArrayIndex(rows, it))
            })

    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Int) {
        array[index] = value
    }

}

class ShortMatrix private constructor(
    override val rows: Int,
    override val cols: Int,
    val array: ShortArray
) : IArrayMatrix<Short> {

    constructor(rows: Int, cols: Int) : this(rows, cols, ShortArray(rows * cols))
    constructor(rows: Int, cols: Int, init: Short) : this(rows, cols, ShortArray(rows * cols) { init })
    constructor(rows: Int, cols: Int, init: (row: Int, col: Int) -> Short) :
            this(rows, cols, ShortArray(rows * cols) {
                init(IArrayMatrix.getRowFromArrayIndex(rows, it), IArrayMatrix.getColFromArrayIndex(rows, it))
            })

    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Short) {
        array[index] = value
    }

}

class ByteMatrix private constructor(
    override val rows: Int,
    override val cols: Int,
    val array: ByteArray
) : IArrayMatrix<Byte> {

    constructor(rows: Int, cols: Int) : this(rows, cols, ByteArray(rows * cols))
    constructor(rows: Int, cols: Int, init: Byte) : this(rows, cols, ByteArray(rows * cols) { init })
    constructor(rows: Int, cols: Int, init: (row: Int, col: Int) -> Byte) :
            this(rows, cols, ByteArray(rows * cols) {
                init(IArrayMatrix.getRowFromArrayIndex(rows, it), IArrayMatrix.getColFromArrayIndex(rows, it))
            })

    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Byte) {
        array[index] = value
    }

}

class FloatMatrix private constructor(
    override val rows: Int,
    override val cols: Int,
    val array: FloatArray
) : IArrayMatrix<Float> {

    constructor(rows: Int, cols: Int) : this(rows, cols, FloatArray(rows * cols))
    constructor(rows: Int, cols: Int, init: Float) : this(rows, cols, FloatArray(rows * cols) { init })
    constructor(rows: Int, cols: Int, init: (row: Int, col: Int) -> Float) :
            this(rows, cols, FloatArray(rows * cols) {
                init(IArrayMatrix.getRowFromArrayIndex(rows, it), IArrayMatrix.getColFromArrayIndex(rows, it))
            })

    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Float) {
        array[index] = value
    }

}

class DoubleMatrix private constructor(
    override val rows: Int,
    override val cols: Int,
    val array: DoubleArray
) : IArrayMatrix<Double> {

    constructor(rows: Int, cols: Int) : this(rows, cols, DoubleArray(rows * cols))
    constructor(rows: Int, cols: Int, init: Double) : this(rows, cols, DoubleArray(rows * cols) { init })
    constructor(rows: Int, cols: Int, init: (row: Int, col: Int) -> Double) :
            this(rows, cols, DoubleArray(rows * cols) {
                init(IArrayMatrix.getRowFromArrayIndex(rows, it), IArrayMatrix.getColFromArrayIndex(rows, it))
            })

    override fun get1d(index: Int) = array[index]
    override fun set1d(index: Int, value: Double) {
        array[index] = value
    }

}

