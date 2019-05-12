package com.ltei.ljuutils.matrix

interface IMatrix<T> {

    val rows: Int
    val cols: Int
    val size: Int get() = rows * cols

    fun get(row: Int, col: Int): T
    fun set(row: Int, col: Int, value: T)

    fun getRow(row: Int): List<T>
    fun getCol(col: Int): List<T>

    fun debug() {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                print("${get(r, c)}, ")
            }
            println()
        }
    }

}