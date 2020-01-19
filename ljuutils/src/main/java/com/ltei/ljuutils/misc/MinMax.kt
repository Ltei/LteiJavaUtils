package com.ltei.ljuutils.misc

class MinMax<T: Comparable<T>> {
    private var mMin: T? = null
    private var mMax: T? = null

    val min get() = mMin!!
    val max get() = mMax!!

    fun update(newValue: T) {
        if (mMin == null || newValue < mMin!!) mMin = newValue
        if (mMax == null || newValue > mMax!!) mMax = newValue
    }
}