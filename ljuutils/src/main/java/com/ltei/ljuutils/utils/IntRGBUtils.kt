package com.ltei.ljuutils.utils

import kotlin.math.roundToInt


object IntRGBUtils {
    @JvmStatic
    fun new(r: Int, g: Int, b: Int): Int = (r and 255 shl 16) or (g and 255 shl 8) or (b and 255 shl 0)

    @JvmStatic
    fun setRed(rgb: Int, r: Int): Int = new(r, getGreen(rgb), getBlue(rgb))

    @JvmStatic
    fun setGreen(rgb: Int, g: Int): Int = new(getRed(rgb), g, getBlue(rgb))

    @JvmStatic
    fun setBlue(rgb: Int, b: Int): Int = new(getRed(rgb), getGreen(rgb), b)

    @JvmStatic
    fun getRed(rgb: Int): Int = rgb shr 16 and 255

    @JvmStatic
    fun getGreen(rgb: Int): Int = rgb shr 8 and 255

    @JvmStatic
    fun getBlue(rgb: Int): Int = rgb shr 0 and 255

    @JvmStatic
    fun mix(rgb0: Int, rgb1: Int, ratio01: Float): Int {
        val iRatio = 1f - ratio01
        val r = getRed(rgb0) * iRatio + getRed(rgb1) * ratio01
        val g = getGreen(rgb0) * iRatio + getGreen(rgb1) * ratio01
        val b = getBlue(rgb0) * iRatio + getBlue(rgb1) * ratio01
        return new(r.roundToInt(), g.roundToInt(), b.roundToInt())
    }
}