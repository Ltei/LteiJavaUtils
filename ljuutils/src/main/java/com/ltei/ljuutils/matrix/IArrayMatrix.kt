package com.ltei.ljuutils.matrix

interface IArrayMatrix<T>: IMatrix<T> {

    fun get1d(index: Int): T
    fun set1d(index: Int, value: T)


    override fun get(row: Int, col: Int): T {
        return get1d(getArrayIndex(row, col))
    }

    override fun set(row: Int, col: Int, value: T) {
        set1d(getArrayIndex(row, col), value)
    }

    override fun getRow(row: Int): List<T> {
        return (0 until cols).map { get(row, it) }
    }

    override fun getCol(col: Int): List<T> {
        return (0 until rows).map { get(it, col) }
    }


    fun getArrayIndex(row: Int, col: Int): Int {
        return row + col * rows
    }

    fun getRowFromArrayIndex(arrayIndex: Int): Int {
        return arrayIndex % rows
    }

    fun getColFromArrayIndex(arrayIndex: Int): Int {
        return arrayIndex / rows
    }

}