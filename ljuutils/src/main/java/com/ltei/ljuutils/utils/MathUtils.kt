package com.ltei.ljuutils.utils

import kotlin.math.*

// Rescale

fun Float.rescale(lastV0: Float, lastV1: Float, newV0: Float, newV1: Float) = rescaleTo01(lastV0, lastV1).rescaleFrom01(newV0, newV1)
fun Number.rescale(lastV0: Number, lastV1: Number, newV0: Number, newV1: Number) = rescaleTo01(lastV0, lastV1).rescaleFrom01(newV0, newV1)

// - To 01

fun Number.rescaleTo01(lastV0: Number, lastV1: Number) = toFloat().rescaleTo01(lastV0.toFloat(), lastV1.toFloat())
fun Double.rescaleTo01(lastV0: Double, lastV1: Double): Double {
    require(this in lastV0..lastV1 || this in lastV1..lastV0) { "$this !in $lastV0..$lastV1" }
    return (this - lastV0) / (lastV1 - lastV0)
}
fun Float.rescaleTo01(lastV0: Float, lastV1: Float): Float {
    require(this in lastV0..lastV1 || this in lastV1..lastV0) { "$this !in $lastV0..$lastV1" }
    return (this - lastV0) / (lastV1 - lastV0)
}

// - From 01

fun Number.rescaleFrom01(newV0: Number, newV1: Number) = toFloat().rescaleFrom01(newV0.toFloat(), newV1.toFloat())
fun Float.rescaleFrom01(newV0: Float, newV1: Float): Float {
    require(this in 0f..1f) { "$this !in 0f..1f" }
    return this * (newV1 - newV0) + newV0
}

// Misc

//fun Float.clamp(min: Float, max: Float) = min(max(this, min), max)
fun Float.modulo(mod: Float): Float {
    val result = rem(mod)
    return if (result < 0) result + mod else result
}
fun Int.modulo(mod: Int): Int {
    val result = rem(mod)
    return if (result < 0) result + mod else result
}

fun Float.modulo2PI(): Float = modulo(2f * PI.toFloat())

fun Float.ceilToInt(): Int {
    val toInt = toInt()
    return if (this == toInt.toFloat()) toInt else toInt + 1
}

fun Float.degToRad(): Float {
    val angle = this.modulo(360f)
    return angle.rescale(0f, 360f, 0f, 2f * PI.toFloat())
}

// Array

fun FloatArray.rescaleTo01(min: Float? = min(), max: Float? = max()): FloatArray {
    if (this.isEmpty()) return FloatArray(0)
    min!!
    max!!
    return FloatArray(this.size) { this[it].rescaleTo01(min, max) }
}

fun IntArray.rescaleTo01(min: Int? = min(), max: Int? = max()): FloatArray {
    if (this.isEmpty()) return FloatArray(0)
    min!!
    max!!
    return FloatArray(this.size) { this[it].rescaleTo01(min, max) }
}