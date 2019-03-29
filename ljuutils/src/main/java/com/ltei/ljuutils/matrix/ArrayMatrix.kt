package com.ltei.ljuutils.matrix

class ArrayMatrix<T>(
        override val rows: Int,
        override val cols: Int,
        createArray: (size: Int, init: (index: Int) -> T) -> Array<T>,
        init: (row: Int, col: Int) -> T
): IArrayMatrix<T> {

    val array = createArray.invoke(rows * cols) {
        init(getRowFromArrayIndex(it), getColFromArrayIndex(it))
    }

    override fun get1d(index: Int): T = array[index]

    override fun set1d(index: Int, value: T) {
        array[index] = value
    }

    companion object {
        inline fun <reified T> create(rows: Int, cols: Int, noinline init: (row: Int, col: Int) -> T): ArrayMatrix<T> {
            return ArrayMatrix(
                    rows, cols,
                    Companion::defaultCreateArray,
                    init
            )
        }
        inline fun <reified T> createNull(rows: Int, cols: Int): ArrayMatrix<T?> {
            return ArrayMatrix(
                    rows, cols,
                    Companion::defaultCreateArray,
                    Companion::nullInit
            )
        }

        inline fun <reified T> defaultCreateArray(size: Int, noinline init: (index: Int) -> T): Array<T> {
            return Array(size, init)
        }
        fun <T> nullInit(@Suppress("UNUSED_PARAMETER") row: Int, @Suppress("UNUSED_PARAMETER") col: Int): T? {
            return null
        }
    }

}