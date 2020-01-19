package com.ltei.ljuutils.misc

interface Index2dTransformer {
    fun from2dTo1d(x: Int, y: Int, major: Int): Int
    fun from1dTo2dX(index: Int, major: Int): Int
    fun from1dTo2dY(index: Int, major: Int): Int

    object WidthBased: Index2dTransformer {
        override fun from2dTo1d(x: Int, y: Int, major: Int) = x + y * major
        override fun from1dTo2dX(index: Int, major: Int) = index % major
        override fun from1dTo2dY(index: Int, major: Int) = index / major
    }

    object HeightBased: Index2dTransformer {
        override fun from2dTo1d(x: Int, y: Int, major: Int) = x * major + y
        override fun from1dTo2dX(index: Int, major: Int) = index / major
        override fun from1dTo2dY(index: Int, major: Int) = index % major
    }
}