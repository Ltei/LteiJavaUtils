package com.ltei.ljuutils.matrix

interface IArrayMatrix<T>: IMatrix<T> {

    fun get1d(index: Int): T
    fun set1d(index: Int, value: T)


    override fun get(row: Int, col: Int): T {
        if (row < 0 || row >= rows) throw IndexOutOfBoundsException("$row / $rows")
        if (col < 0 || col >= cols) throw IndexOutOfBoundsException("$col / $cols")
        return get1d(getArrayIndex(row, col))
    }

    override fun set(row: Int, col: Int, value: T) {
        if (row < 0 || row >= rows) throw IndexOutOfBoundsException("$row / $rows")
        if (col < 0 || col >= cols) throw IndexOutOfBoundsException("$col / $cols")
        set1d(getArrayIndex(row, col), value)
    }

    override fun getRow(row: Int): List<T> {
        return (0 until cols).map { get(row, it) }
    }

    override fun getCol(col: Int): List<T> {
        return (0 until rows).map { get(it, col) }
    }


    fun getArrayIndex(row: Int, col: Int): Int {
        if (row < 0 || row >= rows) throw IndexOutOfBoundsException("$row / $rows")
        if (col < 0 || col >= cols) throw IndexOutOfBoundsException("$col / $cols")
        return row + col * rows
    }

    fun getRowFromArrayIndex(index: Int): Int {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("$index / $size")
        return Companion.getRowFromArrayIndex(rows, index)
    }

    fun getColFromArrayIndex(index: Int): Int {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("$index / $size")
        return Companion.getColFromArrayIndex(rows, index)
    }

    companion object {
        fun getRowFromArrayIndex(rows: Int, index: Int): Int = index % rows
        fun getColFromArrayIndex(rows: Int, index: Int): Int = index / rows
    }

}