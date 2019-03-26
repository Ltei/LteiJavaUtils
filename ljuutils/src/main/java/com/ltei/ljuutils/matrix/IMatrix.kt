package com.ltei.ljuutils.matrix

interface IMatrix<T> {

    val rows: Int
    val cols: Int

    fun get(row: Int, col: Int): T
    fun set(row: Int, col: Int, value: T)

    fun getRow(row: Int): List<T>
    fun getCol(col: Int): List<T>

}